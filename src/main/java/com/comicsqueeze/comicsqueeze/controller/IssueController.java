package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.service.ComicIssueService;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/issue/{profileID}/{seriesTitle}/{issueTitle}")
public class IssueController {
    @Autowired
    private loginRegisterService service;
    @Autowired
    private ComicIssueService issueService;
    @GetMapping
    public String displayProfile(@PathVariable("profileID") String profileID, @PathVariable("seriesTitle") String seriesTitle, @PathVariable("issueTitle") String issueTitle, Model model, HttpSession session)
    {
        Member curMember = service.findMember((String)session.getAttribute("username"));
        if(curMember!=null) {
            model.addAttribute("curMember", curMember);
            model.addAttribute("profileID", profileID);
            model.addAttribute("seriesTitle", seriesTitle);
            model.addAttribute("issueTitle", issueTitle);
            Member member = (Member) session.getAttribute("curMember");
            /*GENERATE MOCK data for now*/
            Issue newIssue = new Issue();
            newIssue.setUsername(member.getUsername());
            newIssue.setTitle(issueTitle);
            //Issue issue = issueService.findIssueByTitle(member,seriesTitle,issueTitle);
            /* END OF MOCK DATA */
            member.setCurrentIssue(newIssue);
        }
        return "IssuePage";
    }

}
