package com.metamong.todayido.controller;

import com.metamong.todayido.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<?> searchByKeyword(@RequestParam("keyword") String keyword) {
        List<String> searchResult = searchService.searchByKeyword(keyword);

        if (searchResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("관련 검색어를 찾을 수 없습니다"); // 키워드 없음 메시지 반환
        } else {
            return ResponseEntity.ok(searchResult); // 검색 결과 반환
        }
    }

}



