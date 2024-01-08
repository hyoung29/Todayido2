package com.metamong.todayido.service;

import com.metamong.todayido.dao.AdminDao;
import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dto.AdminDto;
import com.metamong.todayido.dto.ReviewDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.view;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private AdminDao aDao;

//    private final BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder();

    public String adminloginProc(AdminDto admin, HttpSession session, RedirectAttributes rttr) {
        log.info("adminloginProc()");
        String view = null;
        String msg = null;

//        String rPwd = admin.getAdmin_pw();
        admin = aDao.selectAdminIdPw(admin);
        if (admin != null) {
            session.setAttribute("admin", admin);
            view = "redirect:adindex";
            msg = "로그인 성공";
//            if(dbPwd.equals(admin.getAdmin_id())){
//            } else {
//                view = "redirect:adminLogin";
//                msg = "비밀번호가 틀립니다";
//            }
        } else {
            view = "redirect:adminLogin";
            msg = "존재하지 않는 아이디이거나 비밀번호가 틀립니다";
        }


        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public String logout(HttpSession session) {
        log.info("logout()");
        session.invalidate();
        return "redirect:/";
    }

//    /* 댓글 삭제 */
    public String  deleteReview(int review_num){
      String  result = aDao.deleteReview(review_num);
      return "ok";
  }
}



