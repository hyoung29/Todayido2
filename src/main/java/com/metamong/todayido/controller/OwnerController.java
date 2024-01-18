package com.metamong.todayido.controller;

import com.metamong.todayido.dto.MenuDto;
import com.metamong.todayido.dto.OwnerDto;
import com.metamong.todayido.dto.StoreDto;
import com.metamong.todayido.dto.UserDto;
import com.metamong.todayido.service.MapService;
import com.metamong.todayido.service.OwnerService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class OwnerController {
    @Autowired
    private OwnerService oServ;

    @Autowired
    private MapService mServ;

    @GetMapping("ownerJoin")
    public String ownerJoin() {
        log.info("ownerJoin()");
        return "ownerJoin";
    }

    @PostMapping("ownerjoinProc")
    public String ownerjoinProc(OwnerDto owner, RedirectAttributes rttr) {
        log.info("ownerjoinProc()");
        String view = oServ.ownerJoin(owner, rttr);
        return view;
    }

    @GetMapping("ownerLogin")
    public String ownerLogin() {
        log.info("ownerLogin()");
        return "ownerLogin";
    }

    @PostMapping("ownerloginProc")
    public String ownerloginProc(OwnerDto owner, HttpSession session, RedirectAttributes rttr) {
        log.info("ownerloginProc()");
        String view = oServ.ownerloginProc(owner, session, rttr);
        return view;
    }

    @GetMapping("pindex")
    public ModelAndView pindex(StoreDto store) {
        log.info("pindex()");
        ModelAndView mv = oServ.getStore(store);
        return mv;
    }

    @GetMapping("ownerPage")
    public ModelAndView ownerPage(String  business_num) {
        log.info("ownerPage()");
        ModelAndView mv = oServ.getOwner(business_num);
        return mv;
    }

    //가게등록하기
    @GetMapping("pdetail")
    public String pdetail() {
        log.info("pdetail()");
        return "pdetail";
    }

    @PostMapping("pProc")
    public String pProc(MultipartFile files, StoreDto store,HttpSession session, RedirectAttributes rttr) {
        log.info("pProc()");
        String view = oServ.pdetail(files, store, session, rttr);
        return view;
    }

    @GetMapping("pModify")
    public String pModify(){
        log.info("pModify()");
        return "pModify";
    }

    @GetMapping("ownerModify")
    public String ownerModify() {
        log.info("ownerModify()");
        return "ownerModify";
    }

    @PostMapping("ownerPassUpdate")
    public String ownerPassUpdate(OwnerDto owner){
        log.info("ownerPassUpdate()");
        String view = oServ.ownerPassUpdate(owner);
        return view;
    }

    @PostMapping("pModifyl")
    public String updatePDetail(@RequestParam("file") MultipartFile file,
                                OwnerDto pdetail, HttpSession session,
                                RedirectAttributes rttr) {
        log.info("updatePDetail()");


        String updateResult = oServ.updatepModify(file, pdetail, session, rttr);

        if ("Update successful.".equals(updateResult)) {
            rttr.addFlashAttribute("successMessage", "가게 정보가 성공적으로 수정되었습니다.");
        } else {
            rttr.addFlashAttribute("errorMessage", "가게 정보 수정 중 오류가 발생했습니다.");
        }

        return "redirect:/";
    }

    @GetMapping("menuWrite")
    public ModelAndView menuWrite(StoreDto store_num){
        log.info("menuWrite()");
        ModelAndView mv = oServ.getStore(store_num);
        return mv;
    }

    @PostMapping("mProc")
    public String mProc(MultipartFile files, MenuDto menu, HttpSession session, RedirectAttributes rttr){
        log.info("mProc");
        String view = oServ.menuInsert(files, menu, session, rttr);
        return view;
    }

    @GetMapping("ownerReserv")
    public ModelAndView ownerReserv(int pageNum, HttpSession session) {
        log.info("ownerReserv()");
        ModelAndView mv = mServ.getOwnerReservList(pageNum, session);
        return mv;
    }

}

