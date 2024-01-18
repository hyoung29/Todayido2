package com.metamong.todayido.dao;

import com.metamong.todayido.dto.ReservDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.StoreDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservDao {
    //예약하기 메소드
    void insertReserv (ReservDto rdto);

    ReservDto selectReserv(Map<String, Object> revMap);

    int selectReservCnt(String user_id);

    List<StoreDto> selectStoreList(int storeNum);

    void updateReserv(ReservDto rdto);

    void deleteReserv(int resevation_id);

    ReservDto selectOwnerReserv(Map<String, Object> revMap);

    int selectOwnerReservCnt(int store_num);

    int selectStoreNum(String b_num);

}
