package com.metamong.todayido.controller;

import com.metamong.todayido.dto.StoreDto;
import com.metamong.todayido.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")

public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public String searchStore(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            List<StoreDto> store = searchService.searchStore(keyword);
            model.addAttribute("store", store);
        }
        return "searchResult"; // Thymeleaf HTML 파일 이름
    }
}

