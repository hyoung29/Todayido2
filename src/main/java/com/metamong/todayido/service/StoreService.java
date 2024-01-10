package com.metamong.todayido.service;

import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dto.MenuDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.StoreDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@Slf4j
public class StoreService {

    @Autowired
    private DetailDao dDao;

    public ModelAndView getContent(int store_num) {
        log.info("getContent()");
        ModelAndView mv = new ModelAndView();
        StoreDto store = dDao.selectStore(store_num);
        mv.addObject("store", store);
        List<MenuDto> mList = dDao.selectMenu(store_num);
        mv.addObject("mList", mList);
        List<ReviewDto> rList = dDao.selectReview(store_num);
        mv.addObject("rList", rList);

        mv.setViewName("content");
        return mv;
    }
}

