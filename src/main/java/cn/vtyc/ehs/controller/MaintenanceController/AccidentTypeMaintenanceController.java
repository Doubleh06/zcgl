package cn.vtyc.ehs.controller.MaintenanceController;

import cn.vtyc.ehs.controller.BaseController;
import cn.vtyc.ehs.core.BusinessException;
import cn.vtyc.ehs.core.ErrorCode;
import cn.vtyc.ehs.core.JSONResult;
import cn.vtyc.ehs.core.Result;
import cn.vtyc.ehs.core.jqGrid.JqGridResult;
import cn.vtyc.ehs.dao.AccidentTypeDao;
import cn.vtyc.ehs.dao.DeptmentDao;
import cn.vtyc.ehs.dao.EhsDao;
import cn.vtyc.ehs.dto.AccidentTypeJqGridParam;
import cn.vtyc.ehs.dto.DtoTransfer;
import cn.vtyc.ehs.dto.EhsJqGridParam;
import cn.vtyc.ehs.dto.UserDto;
import cn.vtyc.ehs.entity.AccidentType;
import cn.vtyc.ehs.entity.Ehs;
import cn.vtyc.ehs.entity.User;
import cn.vtyc.ehs.entity.security.Constants;
import cn.vtyc.ehs.service.AccidentTypeService;
import cn.vtyc.ehs.service.EhsService;
import cn.vtyc.ehs.util.MyUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/maintenance/accidentType")
public class AccidentTypeMaintenanceController extends BaseController {

    @Autowired
    private AccidentTypeService accidentTypeService;

    @RequestMapping(value = "/list")
    public String deptList(Model model){
        model.addAttribute("menus", getMenus("type_maintenance"));
        return "/maintenance/accidentTypeList";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result grid( AccidentTypeJqGridParam param) {
        PageInfo<AccidentType> pageInfo = accidentTypeService.selectByJqGridParam(param);
        JqGridResult<AccidentType> result = new JqGridResult<>();
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

    @RequestMapping("/insert")
    @ResponseBody
    public Result insert(@Valid @RequestBody JSONObject dto) {
        String name = dto.getString("name");
        AccidentType accidentType = new AccidentType(name);
        accidentTypeService.insert(accidentType);
        return OK;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        accidentTypeService.delete(id);
        return OK;
    }
    @RequestMapping("/get")
    @ResponseBody
    public Result get(@RequestParam Integer id) {
        return new JSONResult(accidentTypeService.select(id));
    }
    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid @RequestBody JSONObject dto) {
        Integer id = dto.getInteger("id");
        String name = dto.getString("name");
        AccidentType accidentType = new AccidentType();
        accidentType.setId(id);
        accidentType.setName(name);
        accidentTypeService.update(accidentType);
        return OK;
    }
}
