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

            view = "redirect:myPage";
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

        mv.setViewName("myPage");
        return mv;
    }

}