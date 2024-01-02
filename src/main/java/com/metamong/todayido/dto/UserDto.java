package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserDto {
    private String user_id;
    private String user_name;
    private String user_password;
    private String user_pnum;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String user_address;
}