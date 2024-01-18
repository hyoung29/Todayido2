package com.metamong.todayido.service;

import com.metamong.todayido.dao.StoreDao;
import com.metamong.todayido.dto.StoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final StoreDao storeDao;

    @Autowired
    public SearchService(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    // 검색 결과 목록
    public List<StoreDto> searchStore(String keyword) {
        return storeDao.searchStore(keyword);
    }
}
