package com.metamong.todayido.controller;

import com.metamong.todayido.dto.SearchDto;
import com.metamong.todayido.service.SearchService;
import com.metamong.todayido.service.StoreService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Slf4j
public class SearchController {

    @Autowired
    private SearchService searchService;


    @GetMapping("/search")
    public ModelAndView searchResult(SearchDto sDto, HttpSession session) {
        log.info("searchResult()");
        ModelAndView mv = searchService.searchStore(sDto ,session);
        return mv;
    }
}
