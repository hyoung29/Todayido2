package com.metamong.todayido.service;

import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dao.ReservDao;
import com.metamong.todayido.dao.StoreDao;
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

    @Autowired
    private StoreDao sDao;
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
        //로그에 "getReservList()" 메시지를 기록
        log.info("getReservList()");
        //ModelAndView 객체를 생성
        ModelAndView mv = new ModelAndView();
        //세션에서 user 정보 받기
        UserDto user = (UserDto)session.getAttribute("user");
        //페이지 번호와 유저 아이디 등의 정보를 담기 위한 HashMap 객체를 생성
        Map<String, Object> revMap = new HashMap<>();
        //HashMap에 페이지 번호를 설정하고, 1을 뺀 값을 저장
        revMap.put("pageNum", Integer.valueOf(pageNum-1));
        //HashMap에 리스트 개수를 설정
        revMap.put("listCnt", Integer.valueOf(1));
        //HashMap에 user_id를 설정
        revMap.put("user_id", user.getUser_id());
        //HashMap을 이용하여 예약 정보 조회
        ReservDto result = rDao.selectReserv(revMap);
        //조회된 예약 정보를 ModelAndView에 "result"라는 이름으로 추가
        mv.addObject("result", result);

        //페이지 번호에 따른 페이징 정보를 계산하기 위한 메서드를 호출하여 그 결과를 받아옴
        String pageHtml = getPaging(revMap);
        //페이징 정보를 ModelAndView에 "paging"이라는 이름으로 추가
        mv.addObject("paging", pageHtml);

        //세션에 현재 페이지 번호를 저장
        session.setAttribute("pageNum", pageNum);
        //화면을 표시할 뷰의 이름을 "myPage"로 설정
        mv.setViewName("myPage");
        //최종적으로 구성된 ModelAndView 객체를 반환 (이를 통해 Controller는 데이터와 뷰 정보를 함께 전달할 수 있음)
        return mv;
    }

    private String getPaging(Map<String, Object> revMap) {
        String pageHtml = null;
        //전체 글개수 구하기(from DB)
        int maxNum = rDao.selectReservCnt((String) revMap.get("user_id"));
        //페이지에 보여질 번호 개수
        int pageCnt = 5;
        //링크용 uri : 기본 - "myPage?
        // 검색 - "myPage?colname=b_title&keyword=4&
        String listName = "myPage?";

        //
        PagingUtil paging = new PagingUtil(maxNum, ((Integer)revMap.get("pageNum"))+1,
                (Integer)revMap.get("listCnt"), pageCnt, listName);

        pageHtml = paging.makePaging();

        return pageHtml;
    }


    //resevationId 매개변수는 삭제할 예약 정보의 고유 식별자
    public String deleteReservation(int resevationId) {
        //로그에 "deleteReservation()" 메시지를 기록
        log.info("deleteReservation()");
        //삭제 작업을 시도하고, 예외가 발생하면 예외를 처리하는 코드 블록
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

    //ReservDto 객체를 매개변수로 받기
    public String updateReservation(ReservDto reserv) {
        //로그에 메시지를 기록
        log.info("updateReservation()");

        String result = null;

        // 예약 시간 정보를 가져와서 ":00"을 추가하여 시간 형식을 맞춤
        String time = reserv.getResevation_time() + ":00";

        //업데이트된 시간 정보를 다시 ReservDto 객체에 설정
        reserv.setResevation_time(time);
        //예약 날짜 정보를 가져옴
        String date = reserv.getReservation_date();
        //슬래시('/')를 기준으로 예약 날짜를 분리
        String[] dateArr = date.split("/");
        //분리된 날짜 정보를 연도-월-일 형식으로 재조합
        date = dateArr[2] + "-" + dateArr[0] + "-" + dateArr[1];
        //업데이트된 날짜 정보를 다시 ReservDto 객체에 설정
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

    //메서드는 두 개의 매개변수를 받음.pageNum은 페이지 번호를, HttpSession session은 세션 정보를 나타냄 ModelAndView 객체를 반환
    public ModelAndView getOwnerReservList(int pageNum, HttpSession session){
        //해당 메서드가 호출되었을 때 로그에 남김
        log.info("getReservOwnerList()");
        ModelAndView mv = new ModelAndView();
        //세션에서 owner 정보를 가져옴
        OwnerDto owner = (OwnerDto)session.getAttribute("owner");
        //소유자의 사업자 등록 번호를 이용하여 해당 소유자의 가게 번호를 데이터베이스에서 조회
        int store_num = rDao.selectStoreNum(owner.getBusiness_num());

        //페이지 번호, 리스트 개수, 가게 번호 등의 정보를 담기 위한 HashMap 객체를 생성
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