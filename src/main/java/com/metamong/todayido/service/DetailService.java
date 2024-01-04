package com.metamong.todayido.service;

import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.StoreDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class DetailService {
    @Autowired
    private DetailDao dDao;

    @Autowired
    PlatformTransactionManager manager;
    @Autowired
    TransactionDefinition definition;


    private int lcnt = 5;

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

    public ReviewDto ReviewInsert(MultipartFile files, ReviewDto review, HttpSession session) {
        log.info("ReviewInsert");

        try {
            fileUpload(files, session, review);
            dDao.insertReview(review);
            review = dDao.selectLastReview(review.getReview_num());
        } catch (Exception e){
            e.printStackTrace();
            review = null;
        }
        return review;
    }

    private void fileUpload(MultipartFile files, HttpSession session, ReviewDto review) throws Exception {
        //이 메소드의 예외처리(파일 저장 실패, 파일 정보 저장 실패)를 호출한 메소드에서 처리하도록 throws를 사용
        log.info("fileUpload()");
        //파일 저장(폴더)
        //파일 저장 위치 처리 : 세션에서 위치(경로) 정보를 구함
        String realPath = session.getServletContext().getRealPath("/");
        log.info(realPath);
        realPath += "upload/";//파일 업로드용 폴더
        //업로드용 폴더가 없으면 자동으로 생성
        File folder = new File(realPath);
        if (!folder.isDirectory()) {
            //isDirectory() - 폴더의 유무 확인 메소드
            //폴더가 있으면 true, 없거나 폴더가 아니면 false
            folder.mkdir();//Make Directory(폴더)
        }
        String oriname = files.getOriginalFilename();
        if (oriname.equals("")) {
            return;//업로드할 파일 없음. 파일 저장 작업 종료
        }

        review.setRv_oriname(oriname);//원래 파일명 저장
        String sysname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));
        //파일명을 밀리초로 변환
        review.setRv_sysname(sysname);
        //파일 저장(upload폴더에)
        File file = new File(realPath + sysname);
        // 경로 : .../.../.../webapp/upload/ ~.jpg
        files.transferTo(file);//하드디스크에 저장
    }
}