package cn.zcgl.service.zcgl;



import cn.zcgl.core.AbstractService;
import cn.zcgl.core.BaseDao;
import cn.zcgl.core.jqGrid.JqGridParam;
import cn.zcgl.dao.first.TZDao;
import cn.zcgl.dto.TZJqGridParam;
import cn.zcgl.entity.zcgl.Tz;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TZService extends AbstractService<Tz> {


    @Autowired
    private TZDao tzDao;

    @Override
    protected BaseDao<Tz> getDao() {
        return tzDao;
    }

    @Override
    protected List<Tz> selectByJqGridParam(JqGridParam jqGridParam) {
        TZJqGridParam param = (TZJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return tzDao.selectBySql("tz",sql.toString());
    }

    public PageInfo<Tz> selectByJqGridParam(TZJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where type = 0 ");
        if(StringUtils.isNotEmpty(param.getSbbm())) {
            sql.append(" and ssbm like '%").append(param.getSbbm()).append("%'");
        }
        return new PageInfo<>(tzDao.selectTZ(sql.toString()));
    }


}
