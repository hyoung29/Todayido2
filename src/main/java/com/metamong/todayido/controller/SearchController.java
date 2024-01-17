package com.metamong.todayido.controller;

import com.metamong.todayido.dto.StoreDto;
import com.metamong.todayido.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<StoreDto> searchResults = searchService.searchStore(keyword);
        model.addAttribute("searchResults", searchResults);
        return "searchResult";  // 검색 결과를 보여줄 Thymeleaf 템플릿
    }
}
