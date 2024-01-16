package com.metamong.todayido.controller;
import com.metamong.todayido.dto.BoardDto;
import com.metamong.todayido.dto.SearchDto;
import com.metamong.todayido.service.BoardService;
import com.metamong.todayido.service.DetailService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
//메인페이지에서 문의사항클릭하면 문의사항 리스트 페이지로 이동
public class BoardController {

    @Autowired
    private DetailService dServ;

    // 글쓰기 폼 페이지
    @GetMapping("qnaWrite")
    public String qnaWrite() {
        log.info("qnaWrite");
        return "qnaWrite";
    }

    //글쓰기 프로세스 완료되면 list페이지로 이동
    @PostMapping("WriteProc")
    public String writeProc(BoardDto board, RedirectAttributes rttr) {
        log.info("writeProc()");
        String view = bServ.boardWrite(board, rttr);
        return view;
    }

    //글 상세 조회 메소드
    @GetMapping("boardDetail")
    public ModelAndView boardDetail(int qna_num) {
        log.info("boardDetail() : {}", qna_num);
        ModelAndView mv = bServ.getBoard(qna_num);
        return mv;
    }

    @Autowired
    private BoardService bServ;

    //    작성글 리스트로 불러오기
    @GetMapping("qnalist")
    public ModelAndView boardList(SearchDto sdto, HttpSession session, String admin_id) {
        log.info("boardList()");
        ModelAndView mv = bServ.getBoardList(sdto, session, admin_id);
        return mv;
    }

    // 글수정 폼 페이지
    @GetMapping("qnaEdit")
    public ModelAndView qnaEdit(int qna_num) {
        log.info("qnaEdit");
        ModelAndView mv = bServ.updateBoard(qna_num);
        return mv;
    }

    //글 수정 프로세스
    @PostMapping("updateProc")
    public String updateBoard(BoardDto board,RedirectAttributes rttr) {
        log.info("UpdateProc()");
        String view = bServ.updateBoard(board, rttr);
        return view;
    }


    @GetMapping("updateForm")
    public ModelAndView updateForm(int b_num) {
        log.info("updateForm()");
        ModelAndView mv = bServ.updateBoard(b_num);
        return mv;
    }

    @GetMapping("rCategory")
    public ModelAndView rCategory(int store_category_id) {
        log.info("rCategory()");
        ModelAndView mv = dServ.getStoreList(store_category_id);
        return mv;
    }


    @GetMapping("cCategory")
    public ModelAndView cCategory(int store_category_id) {
        log.info("cCategory()");
        ModelAndView mv = dServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("bCategory")
    public ModelAndView bCategory(int store_category_id) {
        log.info("bCategory()");
        ModelAndView mv = dServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("pCategory")
    public ModelAndView pCategory(int store_category_id) {
        log.info("pCategory()");
        ModelAndView mv = dServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("detail")
    public ModelAndView detail(int store_num) {
        log.info("detail()");
        ModelAndView mv = dServ.getReview(store_num);
        return mv;
    }
}


