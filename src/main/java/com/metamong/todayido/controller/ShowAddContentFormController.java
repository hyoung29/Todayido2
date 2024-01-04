package com.metamong.todayido.controller;

import com.metamong.todayido.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class ShowAddContentFormController {
    @Autowired
    private StoreService sServ;

    @GetMapping("content")
    public ModelAndView content(int store_num){
        log.info("content()");
        ModelAndView mv = sServ.getContent(store_num);
        return mv;
    }
}