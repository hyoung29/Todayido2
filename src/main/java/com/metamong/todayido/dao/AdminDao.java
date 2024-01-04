package com.metamong.todayido.dao;

import com.metamong.todayido.dto.AdminDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDao {
    //로그인 pw 가져오는 메소드
    String selectPassword(String adminid);
    //로그인 성공 시 회원 정보를 가져오는 메소드
    AdminDto selectAdmin(String adminid);
}
