package cn.zcgl.dto;



import cn.zcgl.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class TZJqGridParam extends JqGridParam {

    private String sbbm;
    private String syr;
    private String yjbm;
    private String ejbm;
    private Integer type;

}
