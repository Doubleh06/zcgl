package cn.vtyc.ehs.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EhsDto {
    private Integer accidentType;
    private String date;
    private String time;
    private String accidentMan;
    private Integer dept;
    private String accidentPlace;
    private String accidentSituation;
    private String reportMan;
    private String uuid;

}
