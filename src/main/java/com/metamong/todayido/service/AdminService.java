package com.metamong.todayido.service;
import com.metamong.todayido.dao.AdminDao;
import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dto.*;
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
public class AdminService {
    private final int lcnt = 3;

    @Autowired
    private AdminDao aDao;

    @Autowired
    private DetailDao dDao;

    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    private TransactionDefinition definition;
//    private final BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder();

    public String adminloginProc(AdminDto admin, HttpSession session, RedirectAttributes rttr) {
        log.info("adminloginProc()");
        String view = null;
        String msg = null;

//        String rPwd = admin.getAdmin_pw();
        admin = aDao.selectAdminIdPw(admin);
        if (admin != null) {
            session.setAttribute("admin", admin);
            view = "redirect:adindex";
            msg = "로그인 성공";
//            if(dbPwd.equals(admin.getAdmin_id())){
//            } else {
//                view = "redirect:adminLogin";
//                msg = "비밀번호가 틀립니다";
//            }
        } else {
            view = "redirect:adminLogin";
            msg = "존재하지 않는 아이디이거나 비밀번호가 틀립니다";
        }



        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public String logout(HttpSession session) {
        log.info("logout()");
        session.invalidate();
        return "redirect:/";
    }

    public ModelAndView getAqnaList(SearchDto sdto, HttpSession session, AdminDto admin_auth) {
        log.info("getAqnaList()");
        ModelAndView mv = new ModelAndView();

        String admin = String.valueOf(admin(admin_auth));
        mv.addObject("admin", admin);

        //DB에서 게시글 가져오기
        int num = sdto.getPageNum();
        if (sdto.getListCnt() == 0) {
            sdto.setListCnt(lcnt);
        }
        //pageNum을 LIMIT 시작 번호로 변경
        sdto.setPageNum((num - 1) * sdto.getListCnt());
        List<BoardDto> bList = aDao.selectAqnaList(sdto);
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

        mv.setViewName("aqnalist");
        return mv;
    }

    private AdminDto admin(AdminDto admin_auth){
        AdminDto admin = aDao.selectAdmin(admin_auth);
        return admin;
    }

    private String getPaging(SearchDto sdto) {
        String pageHtml = null;
        //전체 글개수 구하기(from DB)
        int maxNum = aDao.selectaqnaCnt(sdto);
        //페이지에 보여질 번호 개수
        int pageCnt = 3;
        //링크용 uri : 기본 - "boardList?
        // 검색 - "boardList?colname=b_title&keyword=4&
        String listName = "aqnalist?";

        PagingUtil paging = new PagingUtil(maxNum, sdto.getPageNum(), sdto.getListCnt(), pageCnt, listName);

        pageHtml = paging.makePaging();

        return pageHtml;
    }

    public ModelAndView updateReply(int qna_num) {
        log.info("updateBoard()");
        ModelAndView mv = new ModelAndView();
        //게시글 내용 가져오기
        BoardDto board = aDao.selectAqna(qna_num);
        //파일 목록 가져오기
        //List<BoardFileDto> fList = bDao.selectFileList(qna_num);

        mv.addObject("board", board);
        //mv.addObject("fList", fList);
        //템플릿 지정
        mv.setViewName("aqnaEdit");
        return mv;
    }

    public String updateReply(BoardDto board, HttpSession session, RedirectAttributes rttr){
        log.info("updateBoard()");
        TransactionStatus status = manager.getTransaction(definition);
        String view = null;
        String msg = null;
        try {
            aDao.updatereply(board);
            manager.commit(status);
            view = "redirect:aqnaEdit?qna_num="+ board.getQna_num();
            msg = "수정 성공";
        } catch (Exception e) {
            e.printStackTrace();
            manager.rollback(status);
            view = "redirect:aqnaEdit?qna_num="+ board.getQna_num();
            msg = "수정 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    //    /* 댓글 삭제 */
    public String  deleteReview(int review_num){
        aDao.deleteReview(review_num);
        return "ok";
    }


    public String boardDelete(int qna_num, HttpSession session, RedirectAttributes rttr) {
        log.info("boardDelete");
        TransactionStatus status = manager.getTransaction(definition);
        String view = null;
        String msg = null;

        try {
            aDao.deleteBoard(qna_num);

            manager.commit(status);
            view = "redirect:aqnalist?pageNum=1";//첫번째 페이지로 넘어감
            msg = "삭제되었습니다.";
        }catch (Exception e){
            e.printStackTrace();
            manager.rollback(status);
            view = "redirect:aqnaEdit?qna_num=" + qna_num;
            msg = "삭제 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public ModelAndView getStoreList(int store_category_id) {
        log.info("getStoreList");
        ModelAndView mv = new ModelAndView();
        List<StoreDto> sList = aDao.selectStoreList(store_category_id);
        mv.addObject("sList", sList);
        return mv;
    }

    public ModelAndView getContent(int store_num) {
        log.info("getContent()");
        ModelAndView mv = new ModelAndView();
        StoreDto store = dDao.selectStore(store_num);
        mv.addObject("store", store);
        List<MenuDto> mList = dDao.selectMenu(store_num);
        mv.addObject("mList", mList);
        List<ReviewDto> rList = dDao.selectReview(store_num);
        mv.addObject("rList", rList);

        mv.setViewName("adContent");
        return mv;
    }
}

