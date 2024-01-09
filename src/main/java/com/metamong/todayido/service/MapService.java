package com.metamong.todayido.service;

import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dao.ReservDao;
import com.metamong.todayido.dto.ReservDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.StoreDto;
import com.metamong.todayido.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    public ModelAndView getReservation(int reservation_num){
        log.info("getReservation()");
        ModelAndView mv = new ModelAndView();
        ReservDto reserv = rDao.selectReserv(reservation_num);
        mv.addObject("reservation", reserv);

        List<StoreDto> sList = rDao.selectStoreList(reservation_num);
        mv.addObject("sList", sList);

        mv.setViewName("myPage");
        return mv;
    }
}