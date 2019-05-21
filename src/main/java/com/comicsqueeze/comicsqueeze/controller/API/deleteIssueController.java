package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.service.ComicIssueService;
import com.comicsqueeze.comicsqueeze.service.ComicPageService;
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

    @Autowired
    private ComicIssueService issueService;

    @Autowired
    private ComicPageService pageService;

    @RequestMapping(value ="/deleteIssue", method = RequestMethod.GET)
    public String deleteIssue(Model model, HttpSession session, @RequestParam(value = "issueOfSeries") String issueTitle,
                              @RequestParam(value = "seriesOfIssue") String seriesTitle, @RequestParam(value = "seriesCreator")String username)
    {
        Member member = (Member) session.getAttribute("curMember");
        if(member.getAdminStatus()){
            System.out.println("in delete issue controller");
            System.out.println(issueTitle);
            pageService.deletePages(issueTitle, seriesTitle, username);
            issueService.deleteIssue(issueTitle, seriesTitle, username);
            return "redirect:/series/" + username + "/" + seriesTitle;
        }
        else{
            System.out.println(member.getCurrentIssue().getTitle());
            pageService.deletePages(member.getCurrentIssue().getTitle(), member.getCurrentSeries().getTitle(), member.getUsername());
            issueService.deleteIssue(member.getCurrentIssue().getTitle(), member.getCurrentSeries().getTitle(), member.getUsername());
            return "redirect:/series/" + member.getCurrentSeries().getUsername() + "/" + member.getCurrentSeries().getTitle();
        }
    }
}
