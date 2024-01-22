package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;

@Getter
@Setter

public class ReservDto {
        private String Store_name;
        private String user_id;
        private String user_pnum;
        private int resevation_id;
        private int Store_num;
        private String reservation_date;
        private String resevation_time;
        private int resevation_person;
        private String photo_path;


    }
