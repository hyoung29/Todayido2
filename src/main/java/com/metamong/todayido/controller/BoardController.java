package com.metamong.todayido.controller;

import com.metamong.todayido.dto.BoardDto;
import com.metamong.todayido.dto.BoardFileDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.SearchDto;
import com.metamong.todayido.service.BoardService;
import com.metamong.todayido.service.DetailService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

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
    public String writeProc(@RequestPart List<MultipartFile> files, BoardDto board, HttpSession session, RedirectAttributes rttr) {
        log.info("writeProc()");
        String view = bServ.boardWrite(files, board, session, rttr);
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
    public ModelAndView boardList(SearchDto sdto, HttpSession session) {
        log.info("boardList()");
        ModelAndView mv = bServ.getBoardList(sdto, session);
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
    public String updateBoard(@RequestPart List<MultipartFile> files, BoardDto board, HttpSession session, RedirectAttributes rttr) {
        log.info("UpdateProc()");
        String view = bServ.updateBoard(files, board, session, rttr);
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
    public String cCategory() {
        log.info("cCategory()");
        return "cCategory";
    }

    @GetMapping("bCategory")
    public String bCategory() {
        log.info("bCategory()");
        return "bCategory";
    }

    @GetMapping("pCategory")
    public String pCategory() {
        log.info("pCategory()");
        return "pCategory";
    }

    @GetMapping("reservForm")
    public String reservForm(int store_num, Model model) {
        log.info("reservForm()");
        //DB에서 가게 정보(dto 등) 가져오기
        //정보를 model에 넣기
        return "reservForm";
    }
    @GetMapping("detail")
    public ModelAndView detail(int store_num){
        log.info("detail()");
        ModelAndView mv = dServ.getReview(store_num);
        return mv;
    }
}

