package cn.vtyc.ehs.controller;

import cn.vtyc.ehs.core.JSONResult;
import cn.vtyc.ehs.core.Result;
import cn.vtyc.ehs.core.jqGrid.JqGridResult;
import cn.vtyc.ehs.dao.*;
import cn.vtyc.ehs.dto.ActionDto;
import cn.vtyc.ehs.dto.ActionJqGridParam;
import cn.vtyc.ehs.dto.EhsDto;
import cn.vtyc.ehs.dto.EhsJqGridParam;
import cn.vtyc.ehs.entity.*;
import cn.vtyc.ehs.service.ActionService;
import cn.vtyc.ehs.service.DeptService;
import cn.vtyc.ehs.service.EhsService;
import cn.vtyc.ehs.util.MailUtil;
import cn.vtyc.ehs.util.MyFileUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = "/action")
public class ActionController extends BaseController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private ActionService actionService;
    @Autowired
    private EhsDao ehsDao;
    @Autowired
    private ActionDao actionDao;
    @Autowired
    private Image2Dao image2Dao;
    @Autowired
    private Environment environment;
    @Autowired
    private PersonInfoDao personInfoDao;


    /**************************************************查看Action*********************************************************/
    @RequestMapping(value = "/seeAction")
    public String list(Model model, @RequestParam Integer id) {
        model.addAttribute("menus", getMenus("backstage"));
        model.addAttribute("ehsId", id);
        return "/action/seeAction";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result grid(ActionJqGridParam param) {
        PageInfo<Action> pageInfo = actionService.selectByJqGridParam(param);
        JqGridResult<Action> result = new JqGridResult<>();
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

    @RequestMapping(value = "/address")
    @ResponseBody
    public Result getAddress(@RequestParam String address) {
        return deptService.getAddressResult(address);
    }

    @RequestMapping(value = "/close")
    @ResponseBody
    public Result close(@RequestParam Integer id) {
        Action action = new Action();
        action.setId(id);
        action.setCloseTime(new Date());
        actionDao.updateByPrimaryKeySelective(action);
        return OK;
    }

    /**************************************************创建Action*********************************************************/
    @RequestMapping(value = "/createAction")
    public String photos(Model model, @RequestParam Integer id) {
        model.addAttribute("menus", getMenus("backstage"));
        model.addAttribute("uuid", UUID.randomUUID());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        model.addAttribute("today", sdf.format(new Date()));
        model.addAttribute("depts", deptService.getAddressArray("CZ"));
        model.addAttribute("ehsId", id);
        return "/action/createAction";
    }

    @RequestMapping(value = "insert")
    @ResponseBody
    public Result insert(@RequestBody ActionDto actionDto) throws ParseException {
        //获取图片连接
        String uuid = actionDto.getUuid();
        List<Image2> imageList = image2Dao.selectImgSourceName(uuid);
        String imgUrl = "";
        for (Image2 image : imageList) {
            imgUrl += image.getImgName() + "~" + image.getImgSourceName() + "|";
        }
        Action action = new Action();
        BeanUtils.copyProperties(actionDto, action);
        String date = actionDto.getCloseDate();
        String time = actionDto.getCloseTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        action.setCloseTime(sdf.parse(date + " " + time));
        if (StringUtils.isEmpty(imgUrl)) {
            action.setImgUrl(imgUrl);
        } else {
            action.setImgUrl(imgUrl.substring(0, imgUrl.length() - 1));
        }
        actionDao.insert(action);
        //发送邮件
        //内容拼接
        String content = "行动描述："+action.getDescriptive();
        content += "\r\n";
        content += "关闭时间："+date +" "+time;
        //附件拼接
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        String filePath =  environment.getProperty("static.img.path");
        if (StringUtils.isNotEmpty(action.getImgUrl())){
            for (String name : action.getImgUrl().split("\\|")){
                map.put("name",name);
                map.put("file",filePath+name);
                list.add(map);
            }
            MailUtil.sendEmail("EHS", action.getEmail().split("\\|"), null, content, list);
        }else{
            MailUtil.sendEmail("EHS", action.getEmail().split("\\|"), null, content, null);
        }
        return OK;
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadFile(MultipartFile[] files, @RequestParam String uuid) {
        List<String> subImgs = new ArrayList<>();
        for (MultipartFile file : files) {
            String imgPath = MyFileUtil.saveFile(file);
            subImgs.add(imgPath);
        }
        //图片入库
        List<Image2> imageList = new ArrayList<>();
        for (String subImg : subImgs) {
            Image2 image = new Image2();
            image.setUuid(uuid);
            image.setImgName(subImg.split("~")[0]);
            image.setImgSourceName(subImg.split("~")[1]);
            imageList.add(image);
        }
        image2Dao.insertList(imageList);
        String subImgsString = StringUtils.join(subImgs.toArray(), ",");
        return new JSONResult(subImgsString);
    }

    @RequestMapping(value = "/deleteFile")
    @ResponseBody
    public Result deleteFile(@RequestParam String imgSourceName, @RequestParam String uuid) {
        //获取img_name
        String imgName = image2Dao.selectImgName(imgSourceName, uuid);
        //删除数据库
        image2Dao.deleteFile(imgSourceName, uuid);
        //删除本地文件
        deleteLocalFile(imgName, imgSourceName);
        return OK;
    }

    public boolean deleteLocalFile(String imgName, String imgSourceName) {
        String imgPath = environment.getProperty("static.img.path");
        //windows
        String pathName = imgPath + "" + imgName + "~" + imgSourceName;
        //linux
//        String pathName = imgPath+"/"+imgName+"~"+imgSourceName;
        boolean flag = false;
        File file = new File(pathName);
        if (file.exists() && file.isFile()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    @RequestMapping(value = "/getEmail")
    @ResponseBody
    public Result getEmail(@RequestParam String name) {
        List<PersonInfo> personInfoList = personInfoDao.selectAllByName(name);
        return new JSONResult(personInfoList);
    }
    /**************************************************Action附件*********************************************************/
    @RequestMapping(value = "/enclosureAction")
    public String enclosure(Model model, @RequestParam Integer id, @RequestParam Integer ehsId) {
        model.addAttribute("menus", getMenus("backstage"));
        model.addAttribute("id", id);
        model.addAttribute("ehsId", ehsId);
        return "/action/enclosureAction";
    }


    @RequestMapping(value = "/enclosure/grid")
    @ResponseBody
    public Result enclosureGrid(@RequestParam Integer id) {
        PageInfo<Map> pageInfo = actionService.selectByJqGridParam2(id);
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

    @RequestMapping(value = "/enclosure/download",produces = "application/json")
    @ResponseBody
    public void download(HttpServletResponse response,@RequestParam String imgName) {
        String filePath = environment.getProperty("static.img.path");
        String fileName = imgName;
        File file = new File(filePath+fileName);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开            
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("下载失败");
            } finally {
                if(bis != null){
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @RequestMapping(value = "export")
    @ResponseBody
    public void export(HttpServletResponse response, @RequestParam Integer ehsId) throws IOException {

        StringBuffer sb = new StringBuffer();
        sb.append("where ehs_id = ").append(ehsId);
        //获取数据
        List<Action> actionList = actionDao.selectActionList(sb.toString());
        //excel 表头
        String[] columns = new String[]{"ehsId", "行动描述", "责任人", "责任部门", "责任主管", "要求关闭时间", "实际关闭时间"};
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Action:" + ehsId + "报告列表");

        //创建表头
        HSSFRow header = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        //i-行   j-列
        for (int i = 0; i < actionList.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            for (int j = 0; j < columns.length; j++) {
                row.createCell(0).setCellValue(actionList.get(i).getEhsId());
                row.createCell(1).setCellValue(actionList.get(i).getDescriptive());
                row.createCell(2).setCellValue(actionList.get(i).getResponsibleMan());
                row.createCell(3).setCellValue(actionList.get(i).getResponsibleDept());
                row.createCell(4).setCellValue(actionList.get(i).getResponsibleDirector());
                row.createCell(5).setCellValue(sdf2.format(actionList.get(i).getCloseTime()));
                String realCloseTime = "";
                if (null != actionList.get(i).getRealCloseTime()) {
                    realCloseTime = sdf2.format(actionList.get(i).getRealCloseTime());
                }
                row.createCell(6).setCellValue(realCloseTime);

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
