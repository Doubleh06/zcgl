package cn.vtyc.ehs.service;

import cn.vtyc.ehs.controller.BaseController;
import cn.vtyc.ehs.core.BusinessException;
import cn.vtyc.ehs.core.JSONResult;
import cn.vtyc.ehs.core.Result;
import cn.vtyc.ehs.util.AjaxUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import static cn.vtyc.ehs.core.ErrorCode.HR_INTERFACE_ERROR;

@Service
public class DeptService extends BaseController {

    private Environment environment;

    String hrScheme = "";
    String hrHost = "";
    String hrPath = "";
    String imgPath = "";

    @Autowired
    public DeptService(Environment environment) {
        this.environment = environment;
        hrScheme = environment.getProperty("hr.scheme");
        hrHost = environment.getProperty("hr.host");
        hrPath = environment.getProperty("hr.path") ;
        imgPath = environment.getProperty("static.img.path");
    }

    public JSONArray getAddressArray(String address) {
        String result = AjaxUtil.doGet(hrScheme, hrHost, hrPath+address, null);
        JSONObject json = JSONObject.parseObject(result);
        String retMsg = json.getString("msg");
        if (!"Data fetched successfully".equals(retMsg)){
            throw new BusinessException(HR_INTERFACE_ERROR);
        }
        return json.getJSONArray("data");
    }

    public Result getAddressResult(String address) {
        String result = AjaxUtil.doGet(hrScheme, hrHost, hrPath+address, null);
        JSONObject json = JSONObject.parseObject(result);
        String retMsg = json.getString("msg");
        if (!"Data fetched successfully".equals(retMsg)){
            throw new BusinessException(HR_INTERFACE_ERROR);
        }
        return new JSONResult(json.getJSONArray("data"));
    }
}
