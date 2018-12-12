package cn.vtyc.ehs.dao;

import cn.vtyc.ehs.core.BaseDao;
import cn.vtyc.ehs.entity.Action;
import cn.vtyc.ehs.entity.Ehs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ActionDao extends BaseDao<Action> {

    @Select("select * from action ${sql}")
    List<Map> selectActionList(@Param("sql") String sql);

    @Select("select img_url from action where id = ${id}")
    String selectImgUrlById(@Param("id") Integer id);
}
