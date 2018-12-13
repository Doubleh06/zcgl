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
    List<Action> selectActionList(@Param("sql") String sql);

    @Select("select * from action ${sql}")
    Action selectAction(@Param("sql") String sql);

    @Select("select img_url from action where id = ${id}")
    String selectImgUrlById(@Param("id") Integer id);

    @Select("select count(*) from action where ehs_id = ${id} ")
    Integer getTotalById(@Param("id")Integer id);


}
