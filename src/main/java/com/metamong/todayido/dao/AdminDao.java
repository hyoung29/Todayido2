package com.metamong.todayido.dao;
import com.metamong.todayido.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminDao {
     void deleteReview(int review_num);

    //관리자 로그인 PW 가져오는 메소드
    AdminDto selectAdminIdPw(AdminDto admin);

    int selectaqnaCnt(SearchDto sdto);

    List<BoardDto> selectAqnaList(SearchDto sdto);

    BoardDto selectAqna(int qnaNum);

    AdminDto selectAdmin(AdminDto admin);

    void updatereply(BoardDto board);

    void deleteBoard(int qnaNum);

    List<StoreDto> selectStoreList(int storeCategoryId);
}



