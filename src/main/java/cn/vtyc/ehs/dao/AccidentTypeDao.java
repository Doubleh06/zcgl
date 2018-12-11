package cn.vtyc.ehs.dao;

import cn.vtyc.ehs.core.BaseDao;
import cn.vtyc.ehs.entity.AccidentType;
import cn.vtyc.ehs.entity.Deptment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface AccidentTypeDao extends BaseDao<AccidentType> {
    @Select("select * from accident_type ${sql}")
    List<AccidentType> selectAccidentTypeList(@Param("sql") String sql);

    @Select("select name from accident_type where id = ${id}")
    String getNameById (@Param("id")Integer id);


}
