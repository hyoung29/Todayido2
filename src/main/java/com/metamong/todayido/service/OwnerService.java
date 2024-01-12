package com.metamong.todayido.service;

import com.metamong.todayido.dao.OwnerDao;
import com.metamong.todayido.dao.StoreDao;
import com.metamong.todayido.dto.BoardFileDto;
import com.metamong.todayido.dto.OwnerDto;
import com.metamong.todayido.dto.ReviewDto;
import com.metamong.todayido.dto.StoreDto;
import jakarta.mail.Store;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class OwnerService {
    @Autowired
    private OwnerDao oDao;
    @Autowired
    private StoreDao sDao;
    private final BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    private TransactionDefinition definition;

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

    public String pdetail(List<MultipartFile> file, StoreDto pdetail, HttpSession session, RedirectAttributes rttr) {
        log.info("pdetail");
        String view = null;
        String msg = null;

        TransactionStatus status = manager.getTransaction(definition);

        try {
            sDao.insertStore(pdetail);
            fileUpload(file, session, pdetail.getStore_num());

            manager.commit(status);//최종 승인

            view = "redirect:pindex";
            msg = "작성 성공";

        } catch (Exception e) {
            e.printStackTrace();
            // 업로드 실패 메시지를 반환
//            return "File upload failed.";
            manager.rollback(status);//취소
            view = "redirect:pdetail";
            msg = "작성 실패";
        }

        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    private void fileUpload(List<MultipartFile> files, HttpSession session, int bNum) throws Exception {
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
        for (MultipartFile mf : files) {
            //파일명(원래 이름) 추출
            String oriname = mf.getOriginalFilename();
            if (oriname.equals("")) {
                return;//업로드할 파일 없음. 파일 저장 작업 종료
            }
            BoardFileDto bfd = new BoardFileDto();
            bfd.setBf_bnum(bNum);//게시글 번호 저장
            bfd.setBf_oriname(oriname);//원래 파일명 저장
            String sysname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));
            //파일명을 밀리초로 변환
            bfd.setBf_sysname(sysname);
            //파일 저장(upload폴더에)
            File file = new File(realPath + sysname);
            // 경로 : .../.../.../webapp/upload/ ~.jpg
            mf.transferTo(file);//하드디스크에 저장
            //파일 정보 저장(DB)
            //sDao.insertFile(bfd);
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

    public String updatepModify(MultipartFile file, OwnerDto pdetail, HttpSession session, RedirectAttributes rttr) {
        try {
            boolean updateSuccess = updateDetails(file, pdetail);

            if (updateSuccess) {
                return "Update successful.";
            } else {
                return "Update failed.";
            }
        } catch (Exception e) {
            return "An error occurred during the update process.";
        }
    }

    private boolean updateDetails(MultipartFile file, OwnerDto pdetail) {


        try {
            return true;

        } catch (Exception e) {

            return false;
        }
    }
}

