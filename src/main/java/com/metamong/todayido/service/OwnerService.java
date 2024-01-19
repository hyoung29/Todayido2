package com.metamong.todayido.service;

import com.metamong.todayido.dao.DetailDao;
import com.metamong.todayido.dao.OwnerDao;
import com.metamong.todayido.dao.StoreDao;
import com.metamong.todayido.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class OwnerService {
    @Autowired
    private OwnerDao oDao;
    @Autowired
    private StoreDao sDao;
    @Autowired
    private DetailDao dDao;

    private final BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    private TransactionDefinition definition;

    public String ownerJoin(OwnerDto owner, RedirectAttributes rttr) {
        log.info("ownerJoin()");
        String view = null;
        String msg = null;

        String encPwd = pEncoder.encode(owner.getOwner_pwd());

        owner.setOwner_pwd(encPwd);
        try {
            oDao.insertOwner(owner);
            msg = "가입 성공";
            view = "redirect:ownerLogin";
        } catch (Exception e) {
            e.printStackTrace();
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public String ownerloginProc(OwnerDto owner, HttpSession session, RedirectAttributes rttr) {
        log.info("ownerloginProc()");
        String view = null;
        String msg = null;

        String rPwd = owner.getOwner_pwd();
        String encPwd = oDao.selectPassword(owner.getBusiness_num());
        if (encPwd != null) {

            if (pEncoder.matches(rPwd, encPwd)) {
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

    public String pdetail(MultipartFile file, StoreDto store, HttpSession session, RedirectAttributes rttr) {
        log.info("pdetail");
        String view = null;
        String msg = null;

        TransactionStatus status = manager.getTransaction(definition);

        try {
            storeFileUpload(file, session, store);
            sDao.insertStore(store);

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

    private void storeFileUpload(MultipartFile files, HttpSession session, StoreDto store) throws Exception {
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

        store.setPhoto_path(oriname);//원래 파일명 저장
        //파일 저장(upload폴더에)
        File file = new File(realPath + oriname);
        // 경로 : .../.../.../webapp/upload/ ~.jpg
        files.transferTo(file);//하드디스크에 저장
    }

    // OwnerDao 가져오기
    public ModelAndView getOwner(String business_num) {
        log.info("ownerSelect()");
        ModelAndView mv = new ModelAndView();
        OwnerDto owner = oDao.ownerSelect(business_num);
        mv.addObject("owner", owner);
        StoreDto store = oDao.store(business_num);
        mv.addObject("store", store);
        return mv;
    }

    public String ownerPassUpdate(OwnerDto owner) {
        log.info("ownerPassUpdate()");
        String view = null;

        String encPwd = pEncoder.encode(owner.getOwner_pwd());
        owner.setOwner_pwd(encPwd);
        try {
            oDao.updateOwnerPwd(owner);
            view = "ownerLogin";
        } catch (Exception e) {
            e.printStackTrace();
            view = "updateOwnerPwd";
        }

        return view;
    }

    public String ownerModifyProc(OwnerDto owner, HttpSession session, RedirectAttributes rttr) {
        log.info("ownerModifyProc()");
        TransactionStatus status = manager.getTransaction(definition);
        String view = null;
        String msg = null;
        try {
            oDao.ownerModifyProc(owner);
            manager.commit(status);
            view = "redirect:ownerPage?business_num=" + owner.getBusiness_num();
            msg = "수정성공";
        } catch (Exception e) {
            e.printStackTrace();
            manager.rollback(status);
            view = "redirect:ownerModify?business_num=" + owner.getBusiness_num();
            msg = "수정실패";
        }
        rttr.addFlashAttribute("msg", msg);
        owner = oDao.selectOwner(owner.getBusiness_num());
        session.setAttribute("owner", owner);

        return view;
    }
    public String modiProc(MultipartFile files, MenuDto menu, HttpSession session, RedirectAttributes rttr) {
        log.info("modiProc()");
        TransactionStatus status = manager.getTransaction(definition);
        String view = null;
        String msg = null;
        String mimg = menu.getMn_sysname();
        try {
            fileUpload(files, session, menu);
            fileDelete(mimg, session);
            oDao.modiProc(menu);
            manager.commit(status);
            view = "redirect:ocontent?store_num=" + menu.getStore_num();
            msg = "수정성공";
        } catch (Exception e) {
            e.printStackTrace();
            manager.rollback(status);
            view = "redirect:menuModi?store_num=" + menu.getStore_num();
            msg = "수정실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }
    public String updatepModify(MultipartFile file, OwnerDto owner, HttpSession session, RedirectAttributes rttr) {
        try {
            boolean updateSuccess = updateDetails(file, owner);

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

    public String menuInsert(MultipartFile file, MenuDto menu, HttpSession session, RedirectAttributes rttr) {
        log.info("menuInsert");
        String view = null;
        String msg = null;

        TransactionStatus status = manager.getTransaction(definition);

        try {
            fileUpload(file, session, menu);

            sDao.insertMenu(menu);

            manager.commit(status);//최종 승인

            view = "redirect:pindex";
            msg = "작성 성공";

        } catch (Exception e) {
            e.printStackTrace();
            // 업로드 실패 메시지를 반환
//            return "File upload failed.";
            manager.rollback(status);//취소
            view = "redirect:menuWrite";
            msg = "작성 실패";
        }

        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    private void fileUpload(MultipartFile files, HttpSession session, MenuDto menu) throws Exception {
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

        menu.setMn_oriname(oriname);//원래 파일명 저장
        String sysname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));
        //파일명을 밀리초로 변환
        menu.setMn_sysname(sysname);
        //파일 저장(upload폴더에)
        File file = new File(realPath + sysname);
        // 경로 : .../.../.../webapp/upload/ ~.jpg
        files.transferTo(file);//하드디스크에 저장
    }

    public ModelAndView getStore(String business_num){
        log.info("getStore()");
        ModelAndView mv = new ModelAndView();
        StoreDto store = oDao.store(business_num);
        mv.addObject("store", store);
        return mv;
    }

    public ModelAndView getStoreList(int store_category_id) {
        log.info("getStoreList");
        ModelAndView mv = new ModelAndView();
        List<StoreDto> sList = oDao.selectStoreList(store_category_id);
        mv.addObject("sList", sList);
        return mv;
    }

    public ModelAndView getOContent(int store_num) {
        log.info("getOContent()");
        ModelAndView mv = new ModelAndView();
        StoreDto store = oDao.selectStore(store_num);
        mv.addObject("store", store);
        List<MenuDto> mList = oDao.selectMenu(store_num);
        mv.addObject("mList", mList);
        List<ReviewDto> rList = oDao.selectReview(store_num);
        mv.addObject("rList", rList);

        mv.setViewName("ocontent");
        return mv;
    }

    public ModelAndView getMenuList(int menu_num) {
        log.info("getMenuList()");
        ModelAndView mv = new ModelAndView();
        MenuDto menu = oDao.menuListSelect(menu_num);
        mv.addObject("menu", menu);
        StoreDto store = oDao.selectStore(menu.getStore_num());
        mv.addObject("store", store);

        mv.setViewName("menuModi");
        return mv;
    }


    private void fileDelete(String mimg,
                            HttpSession session)throws Exception{
        log.info("fileDelete()");
        String realPath = session.getServletContext().getRealPath("/");
        realPath+="upload/" + mimg;
        File file = new File(realPath);
        if (file.exists()){
            file.delete();
        }
    }
}