package cn.vtyc.ehs.dao;

import cn.vtyc.ehs.core.BaseDao;
import cn.vtyc.ehs.entity.AccidentLevel;
import cn.vtyc.ehs.entity.AccidentType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccidentLevelDao extends BaseDao<AccidentLevel> {
    @Select("select * from accident_level ${sql}")
    List<AccidentLevel> selectAccidentTypeList(@Param("sql") String sql);

    @Select("select name from accident_level where id = ${id}")
    String getNameById(@Param("id") Integer id);


}
