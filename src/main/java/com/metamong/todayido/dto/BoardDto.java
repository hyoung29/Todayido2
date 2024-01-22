package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


//Qna 문의사항 게시판 Dto
@Getter
@Setter
public class BoardDto {
    private int qna_num;
    private String qna_title;
    private String qna_contents;
    private String user_id;
    private Timestamp create_at;
    private Timestamp update_at;
    private String qna_reply;
}
