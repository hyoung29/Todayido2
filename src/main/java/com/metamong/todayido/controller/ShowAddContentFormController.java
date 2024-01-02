package com.metamong.todayido.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ShowAddContentFormController {
    @GetMapping("content")
    public String content(){
        log.info("content()");
        return "content";
    }
}

