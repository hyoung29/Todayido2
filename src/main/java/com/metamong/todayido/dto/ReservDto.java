package com.metamong.todayido.dto;

import lombok.Getter;
import lombok.Setter;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;

@Getter
@Setter

public class ReservDto {
        private String user_id;
        private String resevation_id;
        private int Store_num;
        private Timestamp reservation_date;
        private Timestamp resevation_time;
        private String resevation_person;


    }
