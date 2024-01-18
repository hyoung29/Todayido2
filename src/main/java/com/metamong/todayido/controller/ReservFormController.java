package com.metamong.todayido.controller;

import com.metamong.todayido.dto.ReservDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.SearchDto;
import com.metamong.todayido.dto.UserDto;
import com.metamong.todayido.service.DetailService;
import com.metamong.todayido.service.MapService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ReservFormController {

    @Autowired
    private MapService mServ;

    @PostMapping("reservProc")
    public String reservProc(ReservDto reserv, HttpSession session, RedirectAttributes rttr) {
        log.info("reservProc()");
        String view = mServ.reservProc(reserv, session, rttr);
        return view;
    }

    @GetMapping("/map")
    public ModelAndView map(int store_num){
        log.info("map()");
        ModelAndView mv = mServ.getMap(store_num);
        return mv;
    }

}
