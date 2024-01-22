package com.metamong.todayido.service;

import com.metamong.todayido.dao.BoardDao;
import com.metamong.todayido.dto.AdminDto;
import com.metamong.todayido.dto.BoardDto;
import com.metamong.todayido.dto.SearchDto;
import com.metamong.todayido.util.PagingUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
@Slf4j

public class BoardService {

    @Autowired
    private BoardDao bDao;

    private final int lcnt = 3;//한 화면(페이지)에 보여질 글 개수
    //트랜젝션 관련 객체 선언

    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    private TransactionDefinition definition;

    public ModelAndView getBoardList(SearchDto sdto, HttpSession session, String admin_id) {
        log.info("getBoardList()");
        ModelAndView mv = new ModelAndView();

        String admin = String.valueOf(admin(admin_id));
        mv.addObject("admin", admin);

        //DB에서 게시글 가져오기
        int num = sdto.getPageNum();
        if (sdto.getListCnt() == 0) {
            sdto.setListCnt(lcnt);
        }
        //pageNum을 LIMIT 시작 번호로 변경
        sdto.setPageNum((num - 1) * sdto.getListCnt());
        List<BoardDto> bList = bDao.selectBoardList(sdto);
        //DB에서 가져온 데이터를 mv에 담기.
        mv.addObject("bList", bList);
        //페이징 처리
        sdto.setPageNum(num);//원래 페이지 번호로 환원
        String pageHtml = getPaging(sdto);
        mv.addObject("paging", pageHtml);
        //페이지 번호와 검색 관련 내용을 세션에 저장
        if (sdto.getColname() != null) {
            session.setAttribute("sdto", sdto);
        } else {//검색이 아닐 때는 제거
            session.removeAttribute("sdto");
        }
        //별개로 페이지번호 저장
        session.setAttribute("pageNum", num);

        mv.setViewName("qnalist");
        return mv;
    }

    private AdminDto admin(String admin_id){
        AdminDto admin = bDao.selectQAdmin(admin_id);
        return admin;
    }

    private String getPaging(SearchDto sdto) {
        String pageHtml = null;
        //전체 글개수 구하기(from DB)
        int maxNum = bDao.selectBoardCnt(sdto);
        //페이지에 보여질 번호 개수
        int pageCnt = 3;
        //링크용 uri : 기본 - "boardList?
        // 검색 - "boardList?colname=b_title&keyword=4&
        String listName = "qnalist?";

        PagingUtil paging = new PagingUtil(maxNum, sdto.getPageNum(), sdto.getListCnt(), pageCnt, listName);

        pageHtml = paging.makePaging();

        return pageHtml;
    }

    //게시글, 파일 저장 및 회원 정보 변경 메소드
    public String boardWrite(BoardDto board, RedirectAttributes rttr) {
        log.info("boardWrite()");
        //트랜젝션 상태 처리 객체
        TransactionStatus status = manager.getTransaction(definition);
        String view = null;
        String msg = null;

        try {
            //글 내용 저장.
            bDao.insertBoard(board);
            log.info("게시글 번호 : " + board.getQna_num());



            manager.commit(status);//최종 승인
            view = "redirect:qnalist?pageNum=1";
            msg = "작성 성공";
        } catch (Exception e) {
            e.printStackTrace();
            manager.rollback(status);//취소
            view = "redirect:qnaWrite";
            msg = "작성 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }
    //저장된 게시판 가져오기
    public ModelAndView getBoard(int b_num) {
        log.info("getBoard");
        ModelAndView mv = new ModelAndView();
        BoardDto board = bDao.selectBoard(b_num);
        mv.addObject("board", board);

        mv.setViewName("qnalist");
        return mv;
    }

    public ModelAndView updateBoard(int qna_num) {
        log.info("updateBoard()");
        ModelAndView mv = new ModelAndView();
        //게시글 내용 가져오기
        BoardDto board = bDao.selectBoard(qna_num);

        mv.addObject("board", board);
        //템플릿 지정
        mv.setViewName("qnaEdit");
        return mv;
    }

    public String updateBoard(BoardDto board, RedirectAttributes rttr) {
        log.info("updateBoard()");
        TransactionStatus status = manager.getTransaction(definition);
        String view = null;
        String msg = null;
        try {
            bDao.updateBoard(board);
            manager.commit(status);
            view = "redirect:qnalist?qna_num=" + board.getQna_num();
            msg = "수정 성공";
        } catch (Exception e) {
            e.printStackTrace();
            manager.rollback(status);
            view = "redirect:qnaEdit?qna_num=" + board.getQna_num();
            msg = "수정 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }
}
