package cn.zcgl.dao.first;

import cn.zcgl.core.BaseDao;
import cn.zcgl.entity.zcgl.Tz;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TZDao extends BaseDao<Tz> {
    @Select("select * from tz ${sql}")
    List<Tz> selectTZ(@Param("sql") String sql);

    @Select("select * from tz where type = ${type} and id = ${id} and is_delete = 0")
    Tz selectDetail(@Param("id")Integer id ,@Param("type")Integer type);



}
