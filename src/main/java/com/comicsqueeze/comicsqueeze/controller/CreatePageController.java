package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller

public class CreatePageController {

    @Autowired
    private loginRegisterService service;
    @RequestMapping("/createpage")
    public String home(Model model, HttpSession session)
    {
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        return "CreatePage";
    }

    @RequestMapping("/pageDB")
    public String addPageToDB(Model model, HttpSession session, @RequestParam("username") String username, @RequestParam("seriesTitle") String seriesTitle, @RequestParam("issueTitle") String issueTitle, @RequestParam("pageNumber") String pageNumber)
    {
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        return "IssuePage";
    }

}
