package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
    private String colname;
    private String keyword;
    private int pageNum=1;
    private int listCnt;
    private int store_num;
    private String store_name;
}
