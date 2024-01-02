package com.metamong.todayido.controller;

import com.metamong.todayido.dto.OwnerDto;
import com.metamong.todayido.service.OwnerService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class OwnerController {
    @Autowired
    private OwnerService oServ;

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
        log.info("pindex");
        return "pindex";
    }

    @GetMapping("ownerPage")
    public String ownerPage() {
        log.info("ownerPage()");
        return "ownerPage";
    }

    //가게등록하기
    @GetMapping("pdetail")
    public String pdetail() {
        log.info("pdetail()");
        return "pdetail";
    }

    @PostMapping("/pdetail")
    public String pdetail(@RequestParam("file") MultipartFile file,
                          OwnerDto pdetail, HttpSession session,
                          RedirectAttributes rttr) {
        log.info("pdetail()");
        String uploadResult = oServ.pdetail(file, pdetail, session, rttr);
        if ("File uploaded successfully.".equals(uploadResult)) {
        } else {
        }
        return "redirect:/";
    }
}

