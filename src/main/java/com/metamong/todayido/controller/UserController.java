package com.metamong.todayido.controller;

import com.metamong.todayido.dto.ReservDto;
import com.metamong.todayido.dto.SearchDto;
import com.metamong.todayido.dto.UserDto;
import com.metamong.todayido.service.MapService;
import com.metamong.todayido.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService uServ;

    @Autowired
    private MapService mServ;

    @GetMapping("/")
    public String home(){
        log.info("home()");
        return "index";
    }
    @GetMapping("userJoin")
    public String userJoin(){
        log.info("userJoin()");
        return "userJoin";
    }
    @PostMapping("userjoinProc")
    public String userjoinProc(UserDto user, RedirectAttributes rttr) {
        log.info("userjoinProc()");
        String view = uServ.userJoin(user, rttr);
        return view;
    }
    @GetMapping("userLogin")
    public String userLogin(){
        log.info("userLogin()");
        return "userLogin";
    }
    @PostMapping("userloginProc")
    public String userloginProc(UserDto user, HttpSession session, RedirectAttributes rttr){
        log.info("userloginProc()");
        String view = uServ.userloginProc(user, session, rttr);
        return view;
    }
    @GetMapping("logout")
    public String logout(HttpSession session){
        log.info("logout()");
        String view = uServ.logout(session);
        return view;
    }
    @GetMapping("myPage")
    public ModelAndView reservList(HttpSession session) {
        log.info("reservList()");
        ModelAndView mv = mServ.getReservList(session);
        return mv;
    }
    @GetMapping("findUserId")
    public String findUserId(){
        log.info("findUserId()");
        return "findUserId";
    }

}
