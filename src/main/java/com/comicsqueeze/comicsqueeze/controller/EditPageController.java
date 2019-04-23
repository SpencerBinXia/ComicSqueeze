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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class EditPageController {
    @Autowired
    private loginRegisterService service;
    @Autowired
    private ComicIssueService issueService;
    @Autowired
    private ComicSeriesService comicSeriesService;
    @Autowired
    private ComicPageService comicPageService;
    @RequestMapping("/deletePage/{username}/{seriesTitle}/{issueTitle}/{pageNumber}")
    public String home(Model model, HttpSession session, @PathVariable("username") String username, @PathVariable("seriesTitle") String seriesTitle, @PathVariable("issueTitle") String issueTitle, @PathVariable("pageNumber") int pageNumber){
        System.out.println("HERE");
        Page p = new Page();
        p.setUsername(username);
        p.setSeries(seriesTitle);
        p.setIssue(issueTitle);
        p.setPagenumber(pageNumber);
        comicPageService.deletePage(p);
        model.addAttribute("profileID", username);
        model.addAttribute("seriesTitle", seriesTitle);
        model.addAttribute("issueTitle", issueTitle);
        Member member = service.findMember(username);
        Series series = comicSeriesService.findSeriesByTitle(member.getUsername(),seriesTitle);
        Issue issue = issueService.findIssueByTitle(member.getUsername(), seriesTitle, issueTitle);
        issue.setPages(comicPageService.queryAllPages(member, seriesTitle, issueTitle));
        ArrayList<Page> pages =  issue.getPages();
        model.addAttribute("issue", issue);
        model.addAttribute("pages",pages);

        if(member.getUsername().equals((String)session.getAttribute("username"))) {
            member = (Member) session.getAttribute("curMember");
            model.addAttribute("curMember", member);
            member.setCurrentSeries(series);
            member.setCurrentIssue(issue);
            session.setAttribute("curMember", member);
        }
        else if (session.getAttribute("username") != null)
        {
            model.addAttribute("curMember", (Member)session.getAttribute("curMember"));
        }
        return "IssuePage";
    }
}
