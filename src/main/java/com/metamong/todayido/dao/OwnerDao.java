package com.metamong.todayido.dao;


import com.metamong.todayido.dto.OwnerDto;
import com.metamong.todayido.dto.StoreDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnerDao {
    //사업주 정보 저장(가입, insert) 메소드
    void insertOwner(OwnerDto owner);

    //로그인 pw 가져오는 메소드
    String selectPassword(String business_num);

    //로그인 성공 시 사업주 정보를 가져오는 메소드
    OwnerDto selectOwner(String business_num);

    //사업자 번호로 사업자 정보를 가져오는 메소드
    OwnerDto ownerSelect(String business_num);

    //사업주 비밀번호 재설정
    void updateOwnerPwd(OwnerDto owner);

    //가게번호로 가게정보를 가져오는 메소드
    StoreDto store(StoreDto storeNum);

    //사업주 정보 수정 메소드
    void ownerModifyProc(OwnerDto owner);


    int selectStoreNum(String bNum);
}
