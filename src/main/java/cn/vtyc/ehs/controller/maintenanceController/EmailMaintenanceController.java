package cn.vtyc.ehs.controller.maintenanceController;

import cn.vtyc.ehs.controller.BaseController;
import cn.vtyc.ehs.core.JSONResult;
import cn.vtyc.ehs.core.Result;
import cn.vtyc.ehs.core.jqGrid.JqGridResult;
import cn.vtyc.ehs.dto.AccidentTypeJqGridParam;
import cn.vtyc.ehs.dto.EmailDto;
import cn.vtyc.ehs.dto.EmailJqGridParam;
import cn.vtyc.ehs.entity.AccidentType;
import cn.vtyc.ehs.entity.Email;
import cn.vtyc.ehs.service.AccidentTypeService;
import cn.vtyc.ehs.service.EmailService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/maintenance/email")
public class EmailMaintenanceController extends BaseController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/list")
    public String deptList(Model model){
        model.addAttribute("menus", getMenus("email"));
        return "/maintenance/emailList";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result grid( EmailJqGridParam param) {
        PageInfo<Email> pageInfo = emailService.selectByJqGridParam(param);
        JqGridResult<Email> result = new JqGridResult<>();
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
    public Result insert(@Valid @RequestBody EmailDto emailDto) {
        Email email = new Email();
        BeanUtils.copyProperties(emailDto,email );
        email.setIsUsing(1);
        emailService.insert(email);
        return OK;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        emailService.delete(id);
        return OK;
    }
    @RequestMapping("/get")
    @ResponseBody
    public Result get(@RequestParam Integer id) {
        return new JSONResult(emailService.select(id));
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid @RequestBody EmailDto emailDto) {
        Email email = new Email();
        BeanUtils.copyProperties(emailDto,email );
        emailService.updateSelective(email);
        return OK;
    }
    @RequestMapping("/chooseEmail")
    @ResponseBody
    public Result chooseEmail(@RequestParam String address) {
        return new JSONResult(emailService.getEmail(address));
    }

    @RequestMapping("/changeEmail")
    @ResponseBody
    public Result changeEmail(@RequestBody JSONObject jsonObject) {
        String address = jsonObject.getString("emailAddress");
        String authName = jsonObject.getString("radio");
        emailService.changeEmailByAuthName(address,authName);
        return OK;
    }
}
