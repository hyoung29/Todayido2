package com.metamong.todayido.service;

import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dao.ReservDao;
import com.metamong.todayido.dto.ReservDto;
import com.metamong.todayido.dto.StoreDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        try {
            //글 내용 저장.
            rDao.insertReserv(reserv);
            log.info("");

            view = "redirect:content";
            msg = "예약 성공";
        } catch (Exception e) {
            e.printStackTrace();

            view = "redirect:content";
            msg = "예약 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

}