package com.metamong.todayido.dao;

import com.metamong.todayido.dto.ReservDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.StoreDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservDao {
    //새로운 예약 정보 추가
    void insertReserv (ReservDto rdto);

    //user 예약 목록 조회
    ReservDto selectReserv(Map<String, Object> revMap);

    //user 예약 내역 갯수
    int selectReservCnt(String user_id);

    //store 정보 조회
    List<StoreDto> selectStoreList(int storeNum);

    // 예약 변경
    void updateReserv(ReservDto rdto);

    //예약 취소
    void deleteReserv(int resevation_id);

    //owner 예약 목록 조회
    ReservDto selectOwnerReserv(Map<String, Object> revMap);

    //owner 예약 내역 갯수
    int selectOwnerReservCnt(int store_num);

    //business_num으로 데이터베이스에서 store_num 조회
    int selectStoreNum(String b_num);

}
