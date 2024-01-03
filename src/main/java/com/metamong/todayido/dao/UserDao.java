package com.metamong.todayido.dao;

import com.metamong.todayido.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    //idcheck용 메소드
    int selectId(String userid);
    //회원 정보 저장(가입, insert) 메소드
    void insertUser(UserDto user);
    //로그인 pw 가져오는 메소드
    String selectPassword(String userid);
    //로그인 성공 시 회원 정보를 가져오는 메소드
    UserDto selectUser(String userid);
    //회원 아이디 가져오는 메소드
    String selectUserId(UserDto user);
}
