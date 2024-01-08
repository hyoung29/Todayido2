package com.metamong.todayido.dao;

import com.metamong.todayido.dto.AdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminDao {
    //관리자 로그인 PW 가져오는 메소드
    AdminDto selectAdminIdPw(AdminDto admin);
}
