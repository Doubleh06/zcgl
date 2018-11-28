package cn.vtyc.ehs.dao;

import cn.vtyc.ehs.core.BaseDao;
import cn.vtyc.ehs.entity.Deptment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface DeptmentDao extends BaseDao<Deptment> {
//    @Select("select *  from user_dept ud left join deptment d on ud.dept_id = d.id where ud.user_id = #{userId}")
//    Deptment getDeptmentByUserId(@Param("userId")Integer userId);
@Select("select dept_name from deptment where id = ${id}")
String getNameById (@Param("id")Integer id);

}
