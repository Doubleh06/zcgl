package cn.vtyc.ehs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Action extends BaseEntity {
    private Integer id;
    private Integer ehsId;
    private String desc;
    private String responsibleMan;
    private String responsibleDept;
    private String responsibleDirector;
    private Date closeTime;
}
