package com.metamong.todayido.controller;

import com.metamong.todayido.dto.ReservDto;
import com.metamong.todayido.service.DetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ReservFormController {
    @Autowired
    private DetailService dServ;

    private ReservDto reservation = new ReservDto();

    @GetMapping("/show")
    public String showReservation(Model model) {
        model.addAttribute("reservation", reservation);
        return "index";
    }

    @PostMapping("/create")
    public String createReservation(@ModelAttribute("newReservation") ReservDto newReservation, Model model) {
        reservation = newReservation;
        model.addAttribute("reservation", reservation);
        return "index";
    }

    @GetMapping("/edit")
    public String editReservationForm(Model model) {
        model.addAttribute("editedReservation", reservation);
        return "edit";
    }

    @PostMapping("/edit")
    public String editReservation(@ModelAttribute("editedReservation") ReservDto editedReservation, Model model) {
        reservation = editedReservation;
        model.addAttribute("reservation", reservation);
        return "index";
    }

    @GetMapping("/cancel")
    public String cancelReservation(Model model) {
        reservation = new ReservDto(); // Reset reservation data
        model.addAttribute("reservation", reservation);
        return "index";
    }

    @GetMapping("/map")
    public ModelAndView map(int a, int b, Model model, int store_num){
        log.info("map");
        Map<String, Integer> lmap = new HashMap<>();
        lmap.put("a", a);
        lmap.put("b", b);
        model.addAttribute("lmap", lmap);
        ModelAndView mv = dServ.getReview(store_num);
        return mv;
    }


}
