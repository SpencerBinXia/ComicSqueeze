package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.service.ComicPageService;
import com.comicsqueeze.comicsqueeze.service.WeeklyContributionService;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CreateSeriesCoverController {
    @Autowired
    private loginRegisterService service;
    @RequestMapping("/createseriescover")
    public String home(Model model, HttpSession session) {
        Member curMember = (Member)session.getAttribute("curMember");
        model.addAttribute("curMember", curMember);
        return "CreateSeriesCover";
    }
}
