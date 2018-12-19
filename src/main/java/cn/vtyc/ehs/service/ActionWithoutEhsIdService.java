package cn.vtyc.ehs.service;


import cn.vtyc.ehs.core.AbstractService;
import cn.vtyc.ehs.core.BaseDao;
import cn.vtyc.ehs.core.jqGrid.JqGridParam;
import cn.vtyc.ehs.dao.AccidentTypeDao;
import cn.vtyc.ehs.dao.ActionDao;
import cn.vtyc.ehs.dao.DeptmentDao;
import cn.vtyc.ehs.dto.ActionJqGridParam;
import cn.vtyc.ehs.dto.EhsJqGridParam;
import cn.vtyc.ehs.entity.Action;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ActionWithoutEhsIdService extends AbstractService<Action> {

    @Resource
    private ActionDao actionDao;
    @Autowired
    private AccidentTypeDao accidentTypeDao;
    @Autowired
    private DeptmentDao deptmentDao;




    @Override
    protected BaseDao<Action> getDao() {
        return actionDao;
    }

    @Override
    protected List<Action> selectByJqGridParam(JqGridParam jqGridParam) {
        EhsJqGridParam param = (EhsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return actionDao.selectBySql("action",sql.toString());
    }


    public PageInfo<Action> selectByJqGridParam(ActionJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getResponsibleMan())){
            sql.append(" and responsible_man like '%").append(param.getResponsibleMan()).append("%'");
        }
        if(StringUtils.isNotEmpty(param.getAddress())){
            sql.append(" and address = '").append(param.getAddress()).append("'");
        }
        if(StringUtils.isNotEmpty(param.getResponsibleDept())){
            sql.append(" and responsible_dept = '").append(param.getResponsibleDept()).append("'");
        }
        if(StringUtils.isNotEmpty(param.getResponsibleDirector())){
            sql.append(" and responsible_director like '%").append(param.getResponsibleDirector()).append("%'");
        }
        //获取ehs信息
        List<Action> actionList = actionDao.selectActionList(sql.toString());
        return new PageInfo<>(actionList);
    }

    public PageInfo<Map> selectByJqGridParam2(Integer id){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        sql.append("and id = ").append(id);

        //获取ehs信息
        Action action = actionDao.selectAction(sql.toString());
        List<Map> imgs = new ArrayList<>();
        for (String img : action.getImgUrl().split("\\|")){
            HashMap<String,String> map = new HashMap<>();
            map.put("imgName",img);
            imgs.add(map);
        }
        return new PageInfo<>(imgs);
    }

    public List<String> getImgUrl(Integer id){
        String imgUrl = actionDao.selectImgUrlById(id);
        List<String> imgUrlList = Arrays.asList(imgUrl.split("\\|"));
        return imgUrlList;
    }
}
