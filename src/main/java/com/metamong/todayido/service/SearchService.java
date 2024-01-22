package com.metamong.todayido.service;

import com.metamong.todayido.dao.StoreDao;
import com.metamong.todayido.dto.SearchDto;
import com.metamong.todayido.dto.SearchMenuDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    StoreDao sdao;


    // 검색 결과 목록
    public ModelAndView searchStore(SearchDto sDto, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        //데이터베이스에서 글 가져오기
        int num = sDto.getPageNum();
        if (sDto.getListCnt() == 0) {
            sDto.setListCnt(sDto.getListCnt());
        }
        //PageNum을 시작 번호로 변경
        sDto.setPageNum((num - 1) * sDto.getListCnt());
        List<SearchMenuDto> mList = sdao.searchStore(sDto.getKeyword());
        mv.addObject("results", mList);

//
//        //페이징 처리
//        sDto.setPageNum(num);
//        String pageHtml = getPaging(sDto);
//        mv.addObject("paging", pageHtml);


        //페이지 번호와 검색 관련 내용을 세션에 저장
        if (sDto.getColname() != null) {
            session.setAttribute("sDto", sDto);
        } else {
            //검색이 아닐 때는 제거
            session.removeAttribute("sDto");
        }
        //페이지 번호 저장
        session.setAttribute("pageNum", num);

        mv.setViewName("searchResult");
        return mv;
    }



}
