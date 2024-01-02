package com.metamong.todayido.service;

import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.StoreDto;
import com.metamong.todayido.dto.ReviewFileDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DetailService {
    @Autowired
    private DetailDao dDao;

    public ModelAndView getReview(int store_num){
        log.info("getReview()");
        ModelAndView mv = new ModelAndView();
        StoreDto store = dDao.selectStore(store_num);
        mv.addObject("store", store);
        List<ReviewDto> rList = dDao.selectReview(store_num);
        mv.addObject("rList", rList);

        mv.setViewName("detail");
        return mv;
    }

    public ReviewDto ReviewInsert(ReviewDto review) {
        log.info("ReviewInsert");

        try {
            dDao.insertReview(review);
            review = dDao.selectLastReview(review.getReview_num());
        } catch (Exception e){
            e.printStackTrace();
            review = null;
        }
        return review;
    }

    private void fileUpload(List<MultipartFile> files, HttpSession session, int review_num) throws IOException {
        log.info("fileUpload()");

        String realPath = session.getServletContext().getRealPath("/");
        log.info(realPath);
        realPath += "upload";

        File folder = new File(realPath);
        if (folder.isDirectory() == false){
            folder.mkdir();
        }

        for (MultipartFile mf : files){
            String oriname = mf.getOriginalFilename();
            if (oriname.equals("")){
                return;
            }

            ReviewFileDto rfd = new ReviewFileDto();
            rfd.setReview_num(review_num);
            rfd.setRv_oriname(oriname);
            String sysname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));
            rfd.setRv_sysname(sysname);

            File file = new File(realPath + sysname);

            mf.transferTo(file);

            dDao.insertRfile(rfd);
        }


    }

}