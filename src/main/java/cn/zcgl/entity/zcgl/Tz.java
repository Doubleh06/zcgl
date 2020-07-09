package cn.zcgl.entity.zcgl;


import cn.zcgl.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 菜单资源类（菜单及页面上的按钮链接等资源）
 *
 * @author fonlin
 * @date 2018/4/19
 */
@Data
public class Tz extends BaseEntity {
    private String sbbm;
    private String ggxh;
    private String cpu;
    private String nc;
    private String yp;
    private String xsq;
    private String czxt;
    private Integer num;
    private String dw;
    private Date syrq;
    private String yjbm;
    private String ejbm;
    private String syr;
    private Integer bgr;
    private String gmjg;
    private String gys;
    private String synx;
    private String xzcz;
    private String wxjl;
    private String memo;
    private Integer type;//0：笔记本  1：pc
    private Integer isDelete;//0：未删除 1：删除  默认0



}
