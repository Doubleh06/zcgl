package cn.vtyc.ehs.service;


import cn.vtyc.ehs.core.AbstractService;
import cn.vtyc.ehs.core.BaseDao;
import cn.vtyc.ehs.core.jqGrid.JqGridParam;
import cn.vtyc.ehs.dao.AccidentTypeDao;
import cn.vtyc.ehs.dao.DeptmentDao;
import cn.vtyc.ehs.dao.EhsDao;
import cn.vtyc.ehs.dto.AccidentTypeJqGridParam;
import cn.vtyc.ehs.dto.EhsJqGridParam;
import cn.vtyc.ehs.entity.AccidentType;
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
public class AccidentTypeService extends AbstractService<AccidentType> {


    @Autowired
    private AccidentTypeDao accidentTypeDao;

    @Override
    protected BaseDao<AccidentType> getDao() {
        return accidentTypeDao;
    }

    @Override
    protected List<AccidentType> selectByJqGridParam(JqGridParam jqGridParam) {
        EhsJqGridParam param = (EhsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return accidentTypeDao.selectBySql("accident_type",sql.toString());
    }


    public PageInfo<AccidentType> selectByJqGridParam(AccidentTypeJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and name like '%").append(param.getName()).append("%'");
        }
        return new PageInfo<>(accidentTypeDao.selectAccidentTypeList(sql.toString()));
    }

    public void delete(Integer id){
        AccidentType accidentType = new AccidentType();
        accidentType.setId(id);
        accidentTypeDao.delete(accidentType);
    }
}
