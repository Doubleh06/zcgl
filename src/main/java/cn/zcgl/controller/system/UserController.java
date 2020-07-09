package cn.zcgl.controller.system;


import cn.zcgl.controller.BaseController;
import cn.zcgl.core.BusinessException;
import cn.zcgl.core.ErrorCode;
import cn.zcgl.core.JSONResult;
import cn.zcgl.core.Result;
import cn.zcgl.core.jqGrid.JqGridResult;
import cn.zcgl.dto.DtoTransfer;
import cn.zcgl.dto.UserDto;
import cn.zcgl.dto.UserJqGridParam;
import cn.zcgl.dto.UserRoleDto;
import cn.zcgl.entity.Role;
import cn.zcgl.entity.User;
import cn.zcgl.entity.security.Constants;
import cn.zcgl.service.RoleService;
import cn.zcgl.service.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author fonlin
 * @date 2018/4/23
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;



//    @PreAuthorize("hasRole('user')")
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("menus", getMenus("user"));
        return "/system/user/list";
    }

    @RequestMapping("/grid")
    @ResponseBody
    public Result grid(UserJqGridParam param) {
        PageInfo<Map> pageInfo =  userService.selectUserByPage(param);

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

    @RequestMapping("/insert")
    @ResponseBody
    public Result insert(@Valid @RequestBody UserDto dto) {
        if (userService.select("username", dto.getUsername()) != null) {
            throw new BusinessException(ErrorCode.USER_ALREADY_REG);
        }
        User user = DtoTransfer.userDto2Entity(dto);
        user.setPassword(new BCryptPasswordEncoder().encode(Constants.DEFAULT_PASSWORD));
        user.setEnable(true);
        userService.insert(user);

        return OK;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        userService.deleteUser(id);
        return OK;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Result get(@RequestParam Integer id) {
        return new JSONResult(userService.select(id));
    }

    @RequestMapping("/getJoinUserCompany")
    @ResponseBody
    public Result getJoinUserCompany(@RequestParam Integer id) {
        return new JSONResult(userService.getJoinUserCompany(id));
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid @RequestBody UserDto dto) {
        User user = DtoTransfer.userDto2Entity(dto);
        userService.updateSelective(user);
        return OK;
    }


    @RequestMapping("/role")
    @ResponseBody
    public Result role(@RequestParam Integer userId) {
        List<Role> all = roleService.selectAll();
        List<Role> selected = roleService.selectAllByUser(userId);
        JSONArray jsonArray = new JSONArray();
        for (Role role : all) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", role.getId());
            jsonObject.put("name", role.getName());
            jsonObject.put("selected", selected.contains(role));
            jsonArray.add(jsonObject);
        }
        return new JSONResult(jsonArray);
    }

    @RequestMapping("/role/save")
    @ResponseBody
    public Result saveRole(@Valid @RequestBody UserRoleDto userRoleDto) {
        userService.saveRoles(userRoleDto.getUserId(), userRoleDto.getRoleIds());
        return OK;
    }
}
