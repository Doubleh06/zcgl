package cn.vtyc.ehs.controller;

import cn.vtyc.ehs.core.JSONResult;
import cn.vtyc.ehs.core.Result;
import cn.vtyc.ehs.core.jqGrid.JqGridResult;
import cn.vtyc.ehs.dao.AccidentTypeDao;
import cn.vtyc.ehs.dao.ActionDao;
import cn.vtyc.ehs.dao.EhsDao;
import cn.vtyc.ehs.dao.Image2Dao;
import cn.vtyc.ehs.dto.ActionDto;
import cn.vtyc.ehs.dto.EhsDto;
import cn.vtyc.ehs.dto.EhsJqGridParam;
import cn.vtyc.ehs.entity.Action;
import cn.vtyc.ehs.entity.Ehs;
import cn.vtyc.ehs.entity.Image;
import cn.vtyc.ehs.entity.Image2;
import cn.vtyc.ehs.service.DeptService;
import cn.vtyc.ehs.service.EhsService;
import cn.vtyc.ehs.util.MyFileUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = "/action")
public class ActionController extends BaseController {

    @Autowired
    private DeptService deptService;
    @Autowired
    private AccidentTypeDao accidentTypeDao;
    @Autowired
    private EhsService ehsService;
    @Autowired
    private EhsDao ehsDao;
    @Autowired
    private ActionDao actionDao;
    @Autowired
    private Image2Dao image2Dao;
    @Autowired
    private Environment environment;



    @RequestMapping(value = "list")
    public String list(Model model){
        model.addAttribute("menus", getMenus("backstage"));

        return "/action/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result grid( EhsJqGridParam param) {
        PageInfo<Map> pageInfo = ehsService.selectByJqGridParam(param);
        JqGridResult<Map> result = new JqGridResult<>();
        //当前页
        result.setPage(pageInfo.getPageNum());
        //数据总数
        result.setRecords(pageInfo.getTotal());
        //总页数
        result.setTotal(pageInfo.getPages());
        //当前页数据
        result.setRows(pageInfo.getList());
        return new JSONResult(result);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        Ehs ehs = new Ehs();
        ehs.setId(id);
        ehsDao.delete(ehs);
        return OK;
    }

    @RequestMapping(value = "/createAction")
    public String photos(Model model,@RequestParam Integer id){
        model.addAttribute("menus", getMenus("backstage"));
        model.addAttribute("uuid", UUID.randomUUID());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        model.addAttribute("today",sdf.format(new Date()));
        model.addAttribute("depts",deptService.getAddressArray("CZ"));
        model.addAttribute("ehsId",id);
        return "/action/createAction";
    }
    @RequestMapping(value = "insert")
    @ResponseBody
    public Result insert(@RequestBody ActionDto actionDto)throws ParseException {
        //获取图片连接
        String uuid = actionDto.getUuid();
        List<Image2> imageList = image2Dao.selectImgSourceName(uuid);
        String imgUrl = "";
        for (Image2 image : imageList){
            imgUrl += image.getImgName()+"^"+image.getImgSourceName()+"|";
        }
        Action action = new Action();
        BeanUtils.copyProperties(actionDto, action);
        String date = actionDto.getCloseDate();
        String time = actionDto.getCloseTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        action.setCloseTime(sdf.parse(date+" "+time));
        if(StringUtils.isEmpty(imgUrl)){
            action.setImgUrl(imgUrl);
        }else{
            action.setImgUrl(imgUrl.substring(0,imgUrl.length()-1));
        }
        actionDao.insert(action);
        return OK;
    }


    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public Result uploadFile(MultipartFile[] files, @RequestParam String uuid) {
        List<String> subImgs = new ArrayList<>();
        for (MultipartFile file : files) {
            String imgPath  = MyFileUtil.saveFile(file);
            subImgs.add(imgPath);
        }
        //图片入库
        List<Image2> imageList = new ArrayList<>();
        for (String subImg : subImgs){
            Image2 image = new Image2();
            image.setUuid(uuid);
            image.setImgName(subImg.split("\\^")[0]);
            image.setImgSourceName(subImg.split("\\^")[1]);
            imageList.add(image);
        }
        image2Dao.insertList(imageList);
        String subImgsString= StringUtils.join(subImgs.toArray(), ",");
        return new JSONResult(subImgsString);
    }

    @RequestMapping(value = "/deleteFile")
    @ResponseBody
    public Result deleteFile(@RequestParam String imgSourceName, @RequestParam String uuid) {
        //获取img_name
        String imgName = image2Dao.selectImgName(imgSourceName,uuid);
        //删除数据库
        image2Dao.deleteFile(imgSourceName, uuid);
        //删除本地文件
        deleteLocalFile(imgName,imgSourceName);
        return OK;
    }
    public boolean deleteLocalFile(String imgName,String imgSourceName){
        String imgPath = environment.getProperty("static.img.path");
        //windows
        String pathName = imgPath+""+imgName+"^"+imgSourceName;
        //linux
//        String pathName = imgPath+"/"+imgName+"^"+imgSourceName;
        boolean flag = false;
        File file = new File(pathName);
        if (file.exists()&&file.isFile()){
            file.delete();
            flag = true;
        }
        return flag;
    }
    @RequestMapping(value = "/address")
    @ResponseBody
    public Result getAddress(@RequestParam String address) {
        return deptService.getAddressResult(address);
    }

    @RequestMapping(value = "export")
    @ResponseBody
    public void export(HttpServletResponse response) throws IOException {
        //获取数据
        List<Ehs> ehsList =  ehsDao.selectAll();
        //excel 表头
        String[] columns = new String[]{"序号","事故类型", "事故发生时间", "涉及人员","部门", "事发地点", "事故情况", "汇报人","地址"};
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("员工安全事故报告列表");

        //创建表头
        HSSFRow header = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        //i-行   j-列
        for (int i=0;i<ehsList.size();i++){
            HSSFRow row = sheet.createRow(i+1);
            for (int j = 0; j < columns.length; j++) {
                row.createCell(0).setCellValue(ehsList.get(i).getId());
                row.createCell(1).setCellValue(accidentTypeDao.getNameById(ehsList.get(i).getAccidentType()));
                row.createCell(2).setCellValue(sdf2.format(ehsList.get(i).getAccidentTime()));
                row.createCell(3).setCellValue(ehsList.get(i).getAccidentMan());
                row.createCell(4).setCellValue(ehsList.get(i).getDept());
                row.createCell(5).setCellValue(ehsList.get(i).getAccidentPlace());
                row.createCell(6).setCellValue(ehsList.get(i).getAccidentSituation());
                row.createCell(7).setCellValue(ehsList.get(i).getReportMan());
                row.createCell(8).setCellValue(ehsList.get(i).getAddress());

            }
        }



        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=EHS.xls");
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();

    }

}
