package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
    // 검색 대상 컬럼명
    private String colname;
    // 검색어
    private String keyword;
    // 페이지 번호 (기본값 1)
    private int pageNum = 1;
    // 페이지당 표시될 목록 수
    private int listCnt;
    // 가게 번호
    private int store_num;
    // 가게 이름
    private String store_name;
}