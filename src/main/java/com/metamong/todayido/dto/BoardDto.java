package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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
