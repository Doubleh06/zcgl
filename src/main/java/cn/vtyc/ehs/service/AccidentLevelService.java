package cn.vtyc.ehs.service;


import cn.vtyc.ehs.core.AbstractService;
import cn.vtyc.ehs.core.BaseDao;
import cn.vtyc.ehs.core.jqGrid.JqGridParam;
import cn.vtyc.ehs.dao.AccidentLevelDao;
import cn.vtyc.ehs.dao.AccidentTypeDao;
import cn.vtyc.ehs.dto.AccidentTypeJqGridParam;
import cn.vtyc.ehs.dto.EhsJqGridParam;
import cn.vtyc.ehs.entity.AccidentLevel;
import cn.vtyc.ehs.entity.AccidentType;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccidentLevelService extends AbstractService<AccidentLevel> {


    @Autowired
    private AccidentLevelDao accidentLevelDao;

    @Override
    protected BaseDao<AccidentLevel> getDao() {
        return accidentLevelDao;
    }

    @Override
    protected List<AccidentLevel> selectByJqGridParam(JqGridParam jqGridParam) {
        EhsJqGridParam param = (EhsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return accidentLevelDao.selectBySql("accident_level",sql.toString());
    }


    public PageInfo<AccidentLevel> selectByJqGridParam(AccidentTypeJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and name like '%").append(param.getName()).append("%'");
        }
        return new PageInfo<>(accidentLevelDao.selectAccidentTypeList(sql.toString()));
    }

    public void delete(Integer id){
        AccidentLevel accidentLevel = new AccidentLevel();
        accidentLevel.setId(id);
        accidentLevelDao.delete(accidentLevel);
    }
}
