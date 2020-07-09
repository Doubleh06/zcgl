package cn.zcgl.controller.zcgl;


import cn.zcgl.controller.BaseController;

import cn.zcgl.core.JSONResult;
import cn.zcgl.core.Result;
import cn.zcgl.core.jqGrid.JqGridResult;
import cn.zcgl.dto.TZJqGridParam;
import cn.zcgl.entity.zcgl.Tz;
import cn.zcgl.service.zcgl.TZService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/zcgl/tz")
public class TZController extends BaseController {

    @Autowired
    private TZService tzService;

    @RequestMapping(value = "/notebook")
    public String notebook(Model model) {
        model.addAttribute("menus", getMenus("notebook"));
        return "/tz/notebook/list";
    }

    @RequestMapping(value = "/pc")
    public String pc(Model model) {
        model.addAttribute("menus", getMenus("pc"));
        return "/pc/list";
    }

    @RequestMapping(value = "/computers")
    @ResponseBody
    public Result showComputers(TZJqGridParam param) {
        PageInfo<Tz> pageInfo = tzService.selectByJqGridParam(param);
        JqGridResult<Tz> result = new JqGridResult<>();
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

}
