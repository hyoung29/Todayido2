package com.metamong.todayido.controller;
import com.metamong.todayido.dto.AdminDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.BoardDto;
import com.metamong.todayido.dto.SearchDto;
import com.metamong.todayido.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class AdminController {
    @Autowired
    private AdminService aServ;

    //관리자 로그인
    @GetMapping("adminLogin")
    public String adminLogin(){
        log.info("adminLogin()");
        return "adminLogin";
    }

    //관리자 로그인 기능
    @PostMapping("adminloginProc")
    public String adminloginProc(AdminDto admin, HttpSession session, RedirectAttributes rttr) {
        log.info("adminloginProc()");
        String view = aServ.adminloginProc(admin, session, rttr);
        return view;
    }


    //관리자 로그인 후 메인 인덱스 페이지로 이동
    @GetMapping("adindex")
    public String adindex() {
        log.info("adindex");
        return "adindex";
    }

    //관리자 카테고리 댓글삭제
    @PostMapping("deleteReviewProc")
    @ResponseBody
    public String deleteReview(int review_num){
        log.info("deleteReviewProc");
        String result =aServ.deleteReview(review_num);
        return result;
    }

    //관리자 Qna 문의사항 게시판 질문 답변 리스트로 돌아가기
    @GetMapping("aqnalist")
    public ModelAndView aqnalist(SearchDto sdto, HttpSession session, AdminDto admin_auth){
        log.info("aqnalist");
        ModelAndView mv = aServ.getAqnaList(sdto, session, admin_auth);
        return mv;
    }

    //관리자 Qan 문의사항 게시판 답변 쓰기
    @GetMapping("aqnaEdit")
    public ModelAndView aqnaEdit(int qna_num) {
        log.info("aqnaEdit");
        ModelAndView mv = aServ.updateReply(qna_num);
        return mv;
    }

    //관리자 Qna 게시판 댓글 수정
    @PostMapping("replyProc")
    public String updateProc(BoardDto board, HttpSession session, RedirectAttributes rttr){
        log.info("updateProc");
        String view = aServ.updateReply(board, session, rttr);
        return view;
    }

    //관리자 Qna 게시판 리뷰 삭제
    @GetMapping("boardDelete")
    public String boardDelete(int qna_num, HttpSession session, RedirectAttributes rttr){
        log.info("boardDelete()");
        String view = aServ.boardDelete(qna_num, session, rttr);
        return view;
    }

    @GetMapping("adrCategory")
    public ModelAndView adrCategory(int store_category_id) {
        log.info("rCategory()");
        ModelAndView mv = aServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("adcCategory")
    public ModelAndView adcCategory(int store_category_id) {
        log.info("rCategory()");
        ModelAndView mv = aServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("adpCategory")
    public ModelAndView adpCategory(int store_category_id) {
        log.info("pCategory()");
        ModelAndView mv = aServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("adbCategory")
    public ModelAndView adbCategory(int store_category_id) {
        log.info("adbCategory()");
        ModelAndView mv = aServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("adContent")
    public ModelAndView adContent(int store_num){
        log.info("adContent()");
        ModelAndView mv = aServ.getContent(store_num);
        return mv;
    }
}
