package com.metamong.todayido.controller;

import com.metamong.todayido.dto.MenuDto;
import com.metamong.todayido.dto.OwnerDto;
import com.metamong.todayido.dto.StoreDto;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String pindex() {
        log.info("pindex()");
        return "pindex";
    }

    @GetMapping("ownerPage")
    public ModelAndView ownerPage(String business_num) {
        log.info("ownerPage()");
        ModelAndView mv = oServ.getOwner(business_num);
        mv.setViewName("ownerPage");
        return mv;
    }

    //가게등록하기 페이지 이동
    @GetMapping("pdetail")
    public String pdetail() {
        log.info("pdetail()");
        return "pdetail";
    }

    //가게 정보 등록 업로드
    @PostMapping("pProc")
    public String pProc(MultipartFile files, StoreDto store, HttpSession session, RedirectAttributes rttr) {
        log.info("pProc()");
        String view = oServ.pdetail(files, store, session, rttr);
        return view;
    }

    //가게 수정하기 페이지로 이동하는 컨트롤러
    @GetMapping("pModify")
    public String pModify(){
        log.info("pModify()");
        return "pModify";
    }


    @GetMapping("ownerModify")
    public ModelAndView ownerModify(String business_num) {
        log.info("ownerModify()");
        ModelAndView mv = oServ.getOwner(business_num);
        mv.setViewName("ownerModify");
        return mv;
    }

    @PostMapping("ownerModifyProc")
    public String ownerModifyProc(OwnerDto owner, Model model, HttpSession session, RedirectAttributes rttr) {
        log.info("ownerModifyProc()");
        model.addAttribute("owner", owner);
        String view = oServ.ownerModifyProc(owner, session, rttr);
        return view;
    }
    @PostMapping("modiProc")
    public String modiProc(MultipartFile files, MenuDto menu, Model model, HttpSession session, RedirectAttributes rttr){
        log.info("modiProc");
        model.addAttribute("menu", menu);
        String view = oServ.modiProc(files, menu, session, rttr);
        return view;
    }
    @PostMapping("ownerPassUpdate")
    public String ownerPassUpdate(OwnerDto owner){
        log.info("ownerPassUpdate()");
        String view = oServ.ownerPassUpdate(owner);
        return view;
    }

    @PostMapping("pModifyl")
    public String updatePDetail(@RequestParam("file") MultipartFile file,
                                OwnerDto owner, HttpSession session,
                                RedirectAttributes rttr) {
        log.info("updatePDetail()");
        String updateResult = oServ.updatepModify(file, owner, session, rttr);

        if ("Update successful.".equals(updateResult)) {
            rttr.addFlashAttribute("successMessage", "가게 정보가 성공적으로 수정되었습니다.");
        } else {
            rttr.addFlashAttribute("errorMessage", "가게 정보 수정 중 오류가 발생했습니다.");
        }

        return "redirect:/";
    }

    @GetMapping("menuWrite")
    public ModelAndView menuWrite(String business_num){
        log.info("menuWrite()");
        ModelAndView mv = oServ.getStore(business_num);
        return mv;
    }

    @PostMapping("mProc")
    public String mProc(MultipartFile files, MenuDto menu, HttpSession session, RedirectAttributes rttr){
        log.info("mProc");
        String view = oServ.menuInsert(files, menu, session, rttr);
        return view;
    }

    @GetMapping("ownerReserv")
    //메서드는 두 개의 매개변수를 받음
    public ModelAndView ownerReserv(int pageNum, HttpSession session) {
        //로그에 메시지를 기록
        log.info("ownerReserv()");
        //데이터와 뷰 정보를 함께 가지고 있어 웹 페이지에 필요한 데이터를 전달하고, 특정 뷰를 표시하기 위한 정보를 제공
        ModelAndView mv = mServ.getOwnerReservList(pageNum, session);
        return mv;
    }
    @GetMapping("orCategory")
    public ModelAndView rCategory(int store_category_id) {
        log.info("rCategory()");
        ModelAndView mv = oServ.getStoreList(store_category_id);
        return mv;
    }


    @GetMapping("ocCategory")
    public ModelAndView cCategory(int store_category_id) {
        log.info("cCategory()");
        ModelAndView mv = oServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("obCategory")
    public ModelAndView bCategory(int store_category_id) {
        log.info("bCategory()");
        ModelAndView mv = oServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("opCategory")
    public ModelAndView pCategory(int store_category_id) {
        log.info("pCategory()");
        ModelAndView mv = oServ.getStoreList(store_category_id);
        return mv;
    }

    @GetMapping("ocontent")
    public ModelAndView ocontent(int store_num){
        log.info("ocontent()");
        ModelAndView mv = oServ.getOContent(store_num);
        return mv;
    }

    @GetMapping("menuModi")
    public ModelAndView menuModi(int menu_num){
        log.info("menuModi()");
        ModelAndView mv = oServ.getMenuList(menu_num);
        return mv;
    }


}

