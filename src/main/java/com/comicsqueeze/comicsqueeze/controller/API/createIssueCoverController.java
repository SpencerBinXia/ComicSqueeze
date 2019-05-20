package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
@Controller
public class createIssueCoverController {
    @Autowired
    private loginRegisterService service;
    @RequestMapping("/createissuecover")
    public String home(Model model, HttpSession session) {
        Member curMember = service.findMember((String) session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        return "CreateIssueCover";
    }

}
