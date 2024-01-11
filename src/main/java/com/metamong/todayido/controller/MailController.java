package com.metamong.todayido.controller;

import com.metamong.todayido.service.MailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class MailController {
    @Autowired
    private MailService mServ;

    @GetMapping("userSend")
    public String userSend(){
        log.info ("userSend()");
        return "userSend";
    }
    @GetMapping("ownerSend")
    public String ownerSend(){
        log.info ("ownerSend()");
        return "ownerSend";
    }
    @GetMapping("updateUserPwd")
    public String updateUserPwd(String user_id, Model model){
        log.info("updateUserPwd()");
        model.addAttribute("user_id", user_id);
        return "updateUserPwd";
    }
    @GetMapping("updateOwnerPwd")
    public String updateOwnerPwd(String business_num, Model model){
        log.info("updateOwnerPwd()");
        model.addAttribute("business_num", business_num);
        return "updateOwnerPwd";
    }
    @PostMapping("mailConfirm")
    @ResponseBody
    public Map<String, String> mailConfirm(@RequestBody Map<String, Object> mail) throws MessagingException, UnsupportedEncodingException {
        log.info("mailConfirm()");
        Map<String, String> rMap = new HashMap<>();
        String authCode = mServ.sendEmail((String) mail.get("email"));
        rMap.put("authCode", authCode);
        rMap.put("user_id", (String) mail.get("user_id"));
        return rMap;
    }
}
