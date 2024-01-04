package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDto {
    private String admin_id;
    private String admin_pw;
    private int admin_auth;
}
