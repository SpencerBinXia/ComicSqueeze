package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.ComicIssueService;
import com.comicsqueeze.comicsqueeze.service.ComicPageService;
import com.comicsqueeze.comicsqueeze.service.ComicSeriesService;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
@Controller
public class EditPageController {
    @Autowired
    private loginRegisterService service;
    @RequestMapping("/editPage/{editNum}")
    public String home(Model model, HttpSession session, @PathVariable("editNum") String editNumber)
    {
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        model.addAttribute("editNumber",editNumber);
        return "CreatePage";
    }
}
