package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.comicsqueeze.comicsqueeze.service.loginRegisterService;

import javax.servlet.http.HttpSession;

@Controller
public class deleteIssueController {

    @Autowired
    private loginRegisterService service;

    @RequestMapping(value ="/deleteIssue", method = RequestMethod.GET)
    public String deleteIssue(Model model, HttpSession session)
    {
        Member member = (Member) session.getAttribute("curMember");
        System.out.println(member.getCurrentIssue().getTitle());
        System.out.println(member.getCurrentSeries().getTitle());
        return "redirect:/yourprofile";
    }
}
