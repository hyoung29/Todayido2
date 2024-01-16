package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MenuDto {
    private int menu_num;
    private int store_num;
    private String menu_name;
    private int menu_price;
    private String menu_descripton;
    private Timestamp created_at;
    private Timestamp update_at;
    private String mn_oriname;
    private String mn_sysname;
}
