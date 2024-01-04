package com.metamong.todayido.service;

import com.metamong.todayido.dao.OwnerDao;
import com.metamong.todayido.dto.OwnerDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class OwnerService {
    @Autowired
    private OwnerDao oDao;

    private final BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder();

    public String ownerJoin(OwnerDto owner, RedirectAttributes rttr){
        log.info("OwnerJoin()");
        String view = null;
        String msg = null;

        String encPwd = pEncoder.encode(owner.getOwner_pwd());

        owner.setOwner_pwd(encPwd);
        try {
            oDao.insertOwner(owner);
            msg = "가입 성공";
            view = "redirect:ownerLogin";
        } catch (Exception e){
            e.printStackTrace();
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }
    public String ownerloginProc(OwnerDto owner, HttpSession session, RedirectAttributes rttr){
        log.info("ownerloginProc()");
        String view = null;
        String msg = null;

        String rPwd = owner.getOwner_pwd();
        String encPwd = oDao.selectPassword(owner.getBusiness_num());
        if(encPwd != null){

            if(pEncoder.matches(rPwd, encPwd)){
                owner = oDao.selectOwner(owner.getBusiness_num());
                session.setAttribute("owner", owner);
                view = "redirect:pindex";
                msg = "로그인 성공";
            } else {
                view = "redirect:ownerLogin";
                msg = "비밀번호가 틀립니다";
            }
        } else {
            view = "redirect:ownerLogin";
            msg = "존재하지 않는 아이디입니다";
        }

        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public String logout(HttpSession session) {
        log.info("logout()");
        session.invalidate();
        return "redirect:/";
    }

    public String pdetail(MultipartFile file, OwnerDto pdetail, HttpSession session, RedirectAttributes rttr) {
        try {
            // 여기에 파일 업로드 로직을 추가
            if (!file.isEmpty()) {
                // 업로드할 디렉토리 경로 설정 (실제로는 적절한 경로를 지정해야 함)
                String uploadDir = "/resource/directory/";

                // 업로드할 파일명 생성
                String fileName = file.getOriginalFilename();

                // 파일 저장 경로 설정
                Path filePath = Paths.get(uploadDir, fileName);

                // 파일 저장
                Files.write(filePath, file.getBytes());

                // 업로드 성공 메시지를 반환
                return "File uploaded successfully.";
            } else {
                // 업로드할 파일이 없는 경우
                return "No file selected for upload.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 업로드 실패 메시지를 반환
            return "File upload failed.";
        }
    }
    // OwnerDao 가져오기
    public ModelAndView getOwner(int business_num){
        log.info("ownerSelect()");
        ModelAndView mv = new ModelAndView();
        OwnerDto owner = oDao.ownerSelect(business_num);
        mv.addObject("owner", owner);
        return mv;
    }
}

