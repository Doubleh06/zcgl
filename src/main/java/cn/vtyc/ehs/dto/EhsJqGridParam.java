package cn.vtyc.ehs.dto;


import cn.vtyc.ehs.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class EhsJqGridParam extends JqGridParam {

    private String accidentMan;
    private String address;
    private String dept ;
    private Integer accidentType;
    private String startDate;
    private String endDate;

}
