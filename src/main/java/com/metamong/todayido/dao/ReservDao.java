package com.metamong.todayido.dao;

import com.metamong.todayido.dto.ReservDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservDao {
    //예약하기 메소드
    void insertReserv (ReservDto rdto);
}
