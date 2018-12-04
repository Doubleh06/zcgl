package cn.vtyc.ehs.service;


import cn.vtyc.ehs.core.AbstractService;
import cn.vtyc.ehs.core.BaseDao;
import cn.vtyc.ehs.core.jqGrid.JqGridParam;
import cn.vtyc.ehs.dao.AccidentTypeDao;
import cn.vtyc.ehs.dao.DeptmentDao;
import cn.vtyc.ehs.dao.EhsDao;
import cn.vtyc.ehs.dto.EhsJqGridParam;
import cn.vtyc.ehs.entity.AccidentType;
import cn.vtyc.ehs.entity.Deptment;
import cn.vtyc.ehs.entity.Ehs;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class EhsService extends AbstractService<Ehs> {

    @Resource
    private EhsDao ehsDao;
    @Autowired
    private AccidentTypeDao accidentTypeDao;
    @Autowired
    private DeptmentDao deptmentDao;



    @Override
    protected BaseDao<Ehs> getDao() {
        return ehsDao;
    }

    @Override
    protected List<Ehs> selectByJqGridParam(JqGridParam jqGridParam) {
        EhsJqGridParam param = (EhsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return ehsDao.selectBySql("article",sql.toString());
    }


    public PageInfo<Map> selectByJqGridParam(EhsJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getAccidentMan())){
            sql.append(" and accident_man like '%").append(param.getAccidentMan()).append("%'");
        }
        if(null!=param.getDept()){
            sql.append(" and dept = ").append(param.getDept());
        }
        if(null!=param.getAccidentType()){
            sql.append(" and accident_type = ").append(param.getAccidentType());
        }
        //获取部门信息
        List<Deptment> deptmentList = deptmentDao.selectAll();
        //获取事故种类信息
        List<AccidentType> accidentTypeList = accidentTypeDao.selectAll();
        //获取ehs信息
        List<Map> ehsList = ehsDao.selectEhsList(sql.toString());
        //获取用户信息
        for(Map ehs : ehsList){
            for(Deptment deptment : deptmentList){
                if(Integer.parseInt(ehs.get("dept").toString())==deptment.getId()){
                    ehs.put("dept_name",deptment.getDeptName());
                }
            }
            for(AccidentType accidentType : accidentTypeList){
                if(Integer.parseInt(ehs.get("accident_type").toString())==accidentType.getId()){
                    ehs.put("accident_type_name",accidentType.getName());
                }
            }
        }
        return new PageInfo<>(ehsList);
    }

    public List<String> getImgUrl(Integer id){
        String imgUrl = ehsDao.selectImgUrlById(id);
        List<String> imgUrlList = Arrays.asList(imgUrl.split("\\|"));
        return imgUrlList;
    }
}
