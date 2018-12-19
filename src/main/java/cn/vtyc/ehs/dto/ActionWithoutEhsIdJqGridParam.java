package cn.vtyc.ehs.dto;


import cn.vtyc.ehs.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class ActionWithoutEhsIdJqGridParam extends JqGridParam {
    private String address;
    private String responsibleMan;
    private String responsibleDept;
    private String responsibleDirector ;

}
