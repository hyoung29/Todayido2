package com.metamong.todayido.controller;
import com.metamong.todayido.dto.AdminDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class AdminController {
    @Autowired
    private AdminService aServ;

    @GetMapping("adminLogin")
    public String adminLogin(){
        log.info("adminLogin()");
        return "adminLogin";
    }
    @PostMapping("adminloginProc")
    public String adminloginProc(AdminDto admin, HttpSession session, RedirectAttributes rttr) {
        log.info("adminloginProc()");
        String view = aServ.adminloginProc(admin, session, rttr);
        return view;
    }
    @GetMapping("adindex")
    public String adindex() {
        log.info("adindex");
        return "adindex";
    }

    //댓글삭제
    @PostMapping("deleteReviewProc")
    @ResponseBody
    public String deleteReview(int review_num){
        log.info("deleteReviewProc");
        String result =aServ.deleteReview(review_num);
        return result;
    }
}
