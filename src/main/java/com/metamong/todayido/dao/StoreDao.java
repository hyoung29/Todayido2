package com.metamong.todayido.dao;

import com.metamong.todayido.dto.StoreDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreDao {
//    // 단일 가게 정보 조회
//    StoreDto findByStore_num(int num);
    // 모든 가게 목록 조회
    List<StoreDto> findAll();
    // 가게 정보 수정
    void updateStore(StoreDto store);
    // 가게 삭제
    void deleteStore(Long id);
    // 가게 정보 가져오기
    StoreDto selectStore(int storeNum);
    // 키워드 검색 파라미터 입력
    List<StoreDto> searchStore(@Param("keyword") String keyword);
    void insertStore(StoreDto store);
}
