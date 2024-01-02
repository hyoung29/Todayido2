package com.metamong.todayido.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReviewDto {
    private int review_num;
    private String user_id;
    private int store_num;
    private int rating;
    private String review_text;
    private String owner_review_comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp crated_at;
    private Timestamp update_at;
    private String rv_oriname;
    private String rv_sysname;
}
