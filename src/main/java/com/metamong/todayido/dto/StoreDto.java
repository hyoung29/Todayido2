package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StoreDto {
    private int store_num;
    private String store_name;
    private String store_telephone;
    private String store_adress;
    private int store_category_id;
    private Long average_rating;
    private String store_description;
    private String open_time;
    private String closed_day;
    private String owner_name;
    private String business_num;
    private Timestamp create_at;
    private Timestamp updated_at;
    private int position_x;
    private int position_y;
}
