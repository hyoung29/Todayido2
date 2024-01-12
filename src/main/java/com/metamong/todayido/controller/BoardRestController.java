package com.metamong.todayido.controller;

import com.metamong.todayido.dto.BoardDto;
import com.metamong.todayido.dto.BoardFileDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.service.AdminService;
import com.metamong.todayido.service.BoardService;
import com.metamong.todayido.service.DetailService;
import com.metamong.todayido.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
public class BoardRestController {
    @Autowired
    private UserService mServ;

    @Autowired
    private DetailService dServ;

    ;

    @GetMapping("idCheck")
    public String idCheck(String user_id){
        log.info("idCheck()");
        String result = mServ.idCheck(user_id);
        return result;
    }

    @PostMapping("reviewInsert")
    public ReviewDto reviewInsert(@RequestParam("files")MultipartFile files, ReviewDto review, HttpSession session){
        log.info("reviewInsert");
        review = dServ.ReviewInsert(files, review, session);
        return review;
    }
}
