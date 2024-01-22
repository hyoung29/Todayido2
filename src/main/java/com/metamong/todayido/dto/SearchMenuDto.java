package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMenuDto {
    private int menu_num;
    private String menu_name;
    private String store_name;
    private int store_num;
    private String photo_path;
    private String store_adress;
    private float average_rating;
}
