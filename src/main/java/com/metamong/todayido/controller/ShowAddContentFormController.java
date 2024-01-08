package com.metamong.todayido.controller;

import com.metamong.todayido.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@Slf4j
public class ShowAddContentFormController {
    @Autowired
    private MapService mServ;

    @GetMapping("content")
    public ModelAndView content(int store_num){
        log.info("content()");
        ModelAndView mv = mServ.getReserv(store_num);
        return mv;
    }
}

