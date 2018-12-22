package cn.vtyc.ehs.dto;


import cn.vtyc.ehs.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class EmailJqGridParam extends JqGridParam {

    private String authName;
    private String address;

}
