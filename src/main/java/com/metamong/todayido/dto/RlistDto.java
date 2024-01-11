package com.metamong.todayido.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RlistDto {
    private String store_name;
    private String user_id;
    private int reservation_id;
    private int store_num;
    private String reservation_date;
    private String resevation_time;
    private int resevation_person;
}
