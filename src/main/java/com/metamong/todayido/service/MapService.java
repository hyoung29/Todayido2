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

    //store_num을 매개변수로 받아 지도 페이지에 표시할 데이터를 가져옴
    public ModelAndView getMap(int store_num) {
        //메서드가 호출될 때 "getMap()"라는 정보를 로그에 남김
        log.info("getMap()");
        // ModelAndView 객체 생성
        ModelAndView mv = new ModelAndView();
        //DetailDao 객체의 selectStore 메서드를 호출하여 store_num으로 데이터베이스에서 store 정보를 조회
        StoreDto store = dDao.selectStore(store_num);
        //조회된 store정보를 ModelAndView에 "store"라는 이름으로 추가
        mv.addObject("store", store);

        //뷰의 이름을 "map"으로 설정
        mv.setViewName("map");
        //지도 페이지를 렌더링하는 데 필요한 데이터와 뷰의 경로 전달
        return mv;
    }

    //예약 정보 처리 메서드
    public String reservProc (ReservDto reserv, HttpSession session, RedirectAttributes rttr){
        //메서드가 호출될 때 reservProc()라는 정보를 로그에 남김
        log.info("reservProc()");
        //뷰의 경로를 저장하는 변수를 초기화
        String view = null;
        //처리 결과 메시지를 저장하는 변수를 초기화
        String msg = null;
        //세션에서 UserDto를 가져와서 user_id 얻음
        String user_id = ((UserDto)session.getAttribute("user")).getUser_id();
        //가져온 user_id를 예약 정보에 설정
        reserv.setUser_id(user_id);

        //예약 정보를 데이터베이스에 저장
        try {
            // DAO를 통해 예약 정보를 데이터베이스에 저장
            rDao.insertReserv(reserv);
            log.info("");

            //예약 성공 시, myPage로 리다이렉트하고 메시지를 설정
            view = "redirect:myPage?pageNum=1";
            //예약 성공 시의 메시지를 설정
            msg = "예약 성공";
            // 예외가 발생하면 콘솔에 스택 트레이스를 출력
        } catch (Exception e) {
            e.printStackTrace();

            //예약 실패 시, 해당 가게의 콘텐츠 페이지로 리다이렉트하고 메시지를 설정
            view = "redirect:content?store_num=4";
            //예약 실패 시의 메시지를 설정
            msg = "예약 실패";
        }
        //RedirectAttributes를 통해 리다이렉트 시에 속성을 전달
        rttr.addFlashAttribute("msg", msg);

        //처리 결과에 따라 리다이렉트할 뷰의 경로를 반환
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
        //HashMap에 페이지 번호를 추가, 페이지 번호를 0부터 시작하는 형태로 변환
        revMap.put("pageNum", Integer.valueOf(pageNum-1));
        //HashMap에 한 페이지에 표시할 예약 목록의 개수를 추가
        revMap.put("listCnt", Integer.valueOf(1));
        //HashMap에 store_num 추가
        revMap.put("store_num",store_num);
        // DAO를 통해 HashMap에 담긴 정보를 이용하여 소유자의 예약 목록을 데이터베이스에서 조회
        ReservDto result = rDao.selectOwnerReserv(revMap);
        //조회된 예약 목록을 ModelAndView 객체에 "result"라는 이름으로 추가
        mv.addObject("result", result);

        //페이징 처리를 위한 HTML 코드를 생성하는 메서드를 호출하여 페이지 HTML을 가져옴
        String pageHtml = getOwnerPaging(revMap);
        //페이지 HTML을 ModelAndView 객체에 "paging"이라는 이름으로 추가
        mv.addObject("paging", pageHtml);

        //세션에 현재 페이지 번호를 저장
        session.setAttribute("pageNum", pageNum);
        // ModelAndView의 뷰 이름을 "ownerReserv"로 설정
        mv.setViewName("ownerReserv");
        // ModelAndView 객체를 반환
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