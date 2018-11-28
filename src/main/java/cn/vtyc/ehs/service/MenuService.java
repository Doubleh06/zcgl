package cn.vtyc.ehs.service;



import cn.vtyc.ehs.core.AbstractService;
import cn.vtyc.ehs.core.BaseDao;
import cn.vtyc.ehs.core.jqGrid.JqGridParam;
import cn.vtyc.ehs.dao.MenuDao;
import cn.vtyc.ehs.dao.RoleMenuDao;
import cn.vtyc.ehs.entity.Menu;
import cn.vtyc.ehs.entity.RoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fonlin
 * @date 2018/5/22
 */
@Service
public class MenuService extends AbstractService<Menu> {

    @Resource
    private MenuDao menuDao;
    @Resource
    private RoleMenuDao roleMenuDao;

    @Override
    protected BaseDao<Menu> getDao() {
        return menuDao;
    }

    @Override
    protected List<Menu> selectByJqGridParam(JqGridParam param) {
        return null;
    }

    public List<Menu> selectAllByRole(Integer roleId) {
        return menuDao.selectAllByRole(roleId);
    }

    public List<Menu> selectAllByUser(Integer userId) {
        return menuDao.selectAllByUser(userId);
    }

    public List<Menu> selectAllEnabledByUser(Integer userId) {
        return menuDao.selectAllEnabledByUser(userId);
    }

    public void deleteMenu(Integer id){
        Menu menu = new Menu();
        menu.setId(id);
        menuDao.delete(menu);
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenuId(id);
        roleMenuDao.delete(roleMenu);
    }
}
