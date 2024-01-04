package com.metamong.todayido.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private List<String> staticData = Arrays.asList("식당", "장소명", "분위기");

    public List<String> searchByKeyword(String keyword) {
        // 검색어(keyword)를 포함하는 항목들을 반환
        return staticData.stream()
                .filter(data -> data.contains(keyword))
                .collect(Collectors.toList());
    }
}

