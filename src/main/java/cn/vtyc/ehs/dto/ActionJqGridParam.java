package cn.vtyc.ehs.dto;


import cn.vtyc.ehs.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class ActionJqGridParam extends JqGridParam {
    private Integer ehsId;
    private String address;
    private String responsibleMan;
    private String responsibleDept;
    private String responsibleDirector ;
    private String startDate;
    private String endDate;
}
