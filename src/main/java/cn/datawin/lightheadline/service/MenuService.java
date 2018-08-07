package cn.datawin.lightheadline.service;



import cn.datawin.lightheadline.core.AbstractService;
import cn.datawin.lightheadline.core.BaseDao;
import cn.datawin.lightheadline.core.jqGrid.JqGridParam;
import cn.datawin.lightheadline.dao.MenuDao;
import cn.datawin.lightheadline.entity.Menu;
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
}
