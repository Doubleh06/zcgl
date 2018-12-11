package cn.vtyc.ehs.controller;

import cn.vtyc.ehs.core.BusinessException;
import cn.vtyc.ehs.core.JSONResult;
import cn.vtyc.ehs.core.Result;
import cn.vtyc.ehs.core.jqGrid.JqGridResult;
import cn.vtyc.ehs.dao.AccidentTypeDao;
import cn.vtyc.ehs.dao.DeptmentDao;
import cn.vtyc.ehs.dao.EhsDao;
import cn.vtyc.ehs.dto.EhsJqGridParam;

import cn.vtyc.ehs.entity.Ehs;
import cn.vtyc.ehs.service.DeptService;
import cn.vtyc.ehs.service.EhsService;
import cn.vtyc.ehs.util.AjaxUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.vtyc.ehs.core.ErrorCode.HR_INTERFACE_ERROR;

@Controller
@RequestMapping(value = "/backstage")
public class ManagerController extends BaseController {

    @Autowired
    private DeptService deptService;
    @Autowired
    private AccidentTypeDao accidentTypeDao;
    @Autowired
    private EhsService ehsService;
    @Autowired
    private EhsDao ehsDao;


    @RequestMapping(value = "list")
    public String list(Model model){
        model.addAttribute("depts", deptService.getAddressArray("CZ"));
        model.addAttribute("menus", getMenus("backstage"));
        model.addAttribute("accidentTypes",accidentTypeDao.selectAll());
        return "/backstage/list";
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

    @RequestMapping(value = "photos")
    public String photos(Model model,@RequestParam Integer id){
        model.addAttribute("menus", getMenus("backstage"));
        model.addAttribute("imgUrls",ehsService.getImgUrl(id));
        return "/backstage/photos";
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
