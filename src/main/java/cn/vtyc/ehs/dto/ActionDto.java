package cn.vtyc.ehs.dto;

import lombok.Data;

@Data
public class ActionDto {
    private String desc;
    private Integer ehsId;
    private String responsibleMan;
    private String responsibleDept;
    private String responsibleDirector;
    private String closeDate;
    private String closeTime;
    private String imgUrl;
    private String uuid;

}
