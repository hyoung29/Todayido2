package com.metamong.todayido.dao;

import com.metamong.todayido.dto.ReservDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.StoreDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservDao {
    //예약하기 메소드
    void insertReserv (ReservDto rdto);

    List<ReservDto> selectReserv(String user_id);

    List<StoreDto> selectStoreList(int storeNum);

}
