package com.metamong.todayido.service;

import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dao.ReservDao;
import com.metamong.todayido.dto.*;
import com.metamong.todayido.util.PagingUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MapService {

    @Autowired
    private DetailDao dDao;

    @Autowired
    private ReservDao rDao;


    public ModelAndView getMap(int store_num) {
        log.info("getMap()");
        ModelAndView mv = new ModelAndView();
        StoreDto store = dDao.selectStore(store_num);
        mv.addObject("store", store);

        mv.setViewName("map");
        return mv;
    }

    public String reservProc (ReservDto reserv, HttpSession session, RedirectAttributes rttr){
        log.info("reservProc()");
        String view = null;
        String msg = null;
        String user_id = ((UserDto)session.getAttribute("user")).getUser_id();
        reserv.setUser_id(user_id);

        try {
            //글 내용 저장.
            rDao.insertReserv(reserv);
            log.info("");

            view = "redirect:myPage?pageNum=1";
            msg = "예약 성공";
        } catch (Exception e) {
            e.printStackTrace();

            view = "redirect:content?store_num=4";
            msg = "예약 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    private final int lcnt = 1; //한 화면(페이지)에 보여질 글 개수

    public ModelAndView getReservList(int pageNum, HttpSession session){
        log.info("getReservList()");
        ModelAndView mv = new ModelAndView();
        UserDto user = (UserDto)session.getAttribute("user");
        Map<String, Object> revMap = new HashMap<>();
        revMap.put("pageNum", Integer.valueOf(pageNum-1));
        revMap.put("listCnt", Integer.valueOf(1));
        revMap.put("user_id", user.getUser_id());
        ReservDto result = rDao.selectReserv(revMap);
        mv.addObject("result", result);

        String pageHtml = getPaging(revMap);
        mv.addObject("paging", pageHtml);

        session.setAttribute("pageNum", pageNum);
        mv.setViewName("myPage");
        return mv;
    }

    private String getPaging(Map<String, Object> revMap) {
        String pageHtml = null;
        //전체 글개수 구하기(from DB)
        int maxNum = rDao.selectReservCnt((String) revMap.get("user_id"));
        //페이지에 보여질 번호 개수
        int pageCnt = 5;
        //링크용 uri : 기본 - "boardList?
        // 검색 - "boardList?colname=b_title&keyword=4&
        String listName = "myPage?";

        PagingUtil paging = new PagingUtil(maxNum, ((Integer)revMap.get("pageNum"))+1,
                (Integer)revMap.get("listCnt"), pageCnt, listName);

        pageHtml = paging.makePaging();

        return pageHtml;
    }


    public String deleteReservation(int resevationId) {
        log.info("deleteReservation()");
        String result = null;
        try {
            rDao.deleteReserv(resevationId);
            result = "ok";
        }catch (Exception e){
            e.printStackTrace();
            result = "fail";
        }

        return result;
    }

    public String updateReservation(ReservDto reserv) {
        log.info("updateReservation()");
        String result = null;

        String time = reserv.getResevation_time() + ":00";
        reserv.setResevation_time(time);
        String date = reserv.getReservation_date();
        String[] dateArr = date.split("/");
        date = dateArr[2] + "-" + dateArr[0] + "-" + dateArr[1];
        reserv.setReservation_date(date);

        try{
            rDao.updateReserv(reserv);
            result = "ok";
        }catch (Exception e){
            e.printStackTrace();
            result = "fail";
        }

        return result;
    }

    public ModelAndView getOwnerReservList(int pageNum, HttpSession session){
        log.info("getReservOwnerList()");
        ModelAndView mv = new ModelAndView();
        OwnerDto owner = (OwnerDto)session.getAttribute("owner");
        int store_num = rDao.selectStoreNum(owner.getBusiness_num());

        Map<String, Object> revMap = new HashMap<>();
        revMap.put("pageNum", Integer.valueOf(pageNum-1));
        revMap.put("listCnt", Integer.valueOf(1));
        revMap.put("store_num",store_num);
        ReservDto result = rDao.selectOwnerReserv(revMap);
        mv.addObject("result", result);

        String pageHtml = getOwnerPaging(revMap);
        mv.addObject("paging", pageHtml);

        session.setAttribute("pageNum", pageNum);
        mv.setViewName("ownerReserv");
        return mv;
    }

    private String getOwnerPaging(Map<String, Object> revMap) {
        String pageHtml = null;
        //전체 글개수 구하기(from DB)
        int maxNum = rDao.selectOwnerReservCnt((int) revMap.get("store_num"));
        //페이지에 보여질 번호 개수
        int pageCnt = 5;
        //링크용 uri : 기본 - "boardList?
        // 검색 - "boardList?colname=b_title&keyword=4&
        String listName = "ownerReserv?";

        PagingUtil paging = new PagingUtil(maxNum, ((Integer)revMap.get("pageNum"))+1,
                (Integer)revMap.get("listCnt"), pageCnt, listName);

        pageHtml = paging.makePaging();

        return pageHtml;
    }


}