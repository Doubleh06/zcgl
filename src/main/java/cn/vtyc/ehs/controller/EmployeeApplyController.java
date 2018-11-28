package cn.vtyc.ehs.controller;

import cn.vtyc.ehs.core.JSONResult;
import cn.vtyc.ehs.core.Result;
import cn.vtyc.ehs.dao.AccidentTypeDao;
import cn.vtyc.ehs.dao.DeptmentDao;
import cn.vtyc.ehs.dao.EhsDao;
import cn.vtyc.ehs.dao.ImageDao;
import cn.vtyc.ehs.dto.EhsDto;
import cn.vtyc.ehs.entity.Ehs;
import cn.vtyc.ehs.entity.Image;
import cn.vtyc.ehs.util.MyFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeApplyController extends BaseController {

    @Autowired
    private DeptmentDao deptmentDao;
    @Autowired
    private AccidentTypeDao accidentTypeDao;
    @Autowired
    private EhsDao ehsDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private Environment environment;

    @RequestMapping(value = "sheet")
    public String list(Model model){
        model.addAttribute("depts",deptmentDao.selectAll());
        model.addAttribute("accidentTypes",accidentTypeDao.selectAll());
        model.addAttribute("uuid", UUID.randomUUID());
        return "/employee/sheet";
    }

    @RequestMapping(value = "insert")
    @ResponseBody
    public Result insert(@RequestBody EhsDto ehsDto)throws ParseException {
        //获取图片连接
        String uuid = ehsDto.getUuid();
        List<Image> imageList = imageDao.selectImgSourceName(uuid);
        String imgUrl = "";
        for (Image image : imageList){
            imgUrl += image.getImgName()+"^"+image.getImgSourceName()+"|";
        }
        System.out.println("----------------------------------------------------------");
        Ehs ehs = new Ehs();
        BeanUtils.copyProperties(ehsDto, ehs);
        String date = ehsDto.getDate();
        String time = ehsDto.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        ehs.setAccidentTime(sdf.parse(date+" "+time));
        ehs.setImgUrl(imgUrl.substring(0,imgUrl.length()-1));
        ehsDao.insert(ehs);
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
        List<Image> imageList = new ArrayList<>();
        for (String subImg : subImgs){
            Image image = new Image();
            image.setUuid(uuid);
            image.setImgName(subImg.split("\\^")[0]);
            image.setImgSourceName(subImg.split("\\^")[1]);
            imageList.add(image);
        }
        imageDao.insertList(imageList);
        String subImgsString= StringUtils.join(subImgs.toArray(), ",");
        return new JSONResult(subImgsString);
    }

    @RequestMapping(value = "/deleteFile")
    @ResponseBody
    public Result deleteFile(@RequestParam String imgSourceName, @RequestParam String uuid) {
        //获取img_name
        String imgName = imageDao.selectImgName(imgSourceName,uuid);
        //删除数据库
        imageDao.deleteFile(imgSourceName, uuid);
        //删除本地文件
        deleteLocalFile(imgName,imgSourceName);
        return OK;
    }
    public boolean deleteLocalFile(String imgName,String imgSourceName){
        String path = environment.getProperty("static.img.path");
        boolean flag = false;
        File file = new File(path+imgName+"^"+imgSourceName);
        if (file.exists()&&file.isFile()){
            file.delete();
            flag = true;
        }
        return flag;
    }
}
