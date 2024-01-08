package com.metamong.todayido.dao;

import com.metamong.todayido.dto.MenuDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.StoreDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DetailDao {
    //스토어 가져오는 메소드

    List<ReviewDto> selectReview(int store_num);

    void insertReview(ReviewDto review);

    ReviewDto selectLastReview(int review_num);

    StoreDto selectStore(int storeNum);

    List<StoreDto> selectStoreList(int store_category_id);

    List<MenuDto> selectMenu(int store_num);
}
