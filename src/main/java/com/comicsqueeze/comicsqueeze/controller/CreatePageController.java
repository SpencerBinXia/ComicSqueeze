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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller

public class CreatePageController {
    @Autowired
    private ComicSeriesService comicSeriesService;
    @Autowired
    private ComicIssueService issueService;
    @Autowired
    private loginRegisterService service;
    @Autowired
    private ComicPageService comicPageService;
    @RequestMapping("/createpage")
    public String home(Model model, HttpSession session)
    {
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        return "CreatePage";
    }

    @RequestMapping("/pageDB")
    public String addPageToDB(Model model, HttpSession session, @RequestParam("username") String username, @RequestParam("seriesTitle") String seriesTitle, @RequestParam("issueTitle") String issueTitle, @RequestParam("pageNumber") int pageNumber, @RequestParam("imgurl") String imgurl)
    {
        Page page = new Page();
        page.setUsername(username);
        page.setPagenumber(pageNumber);
        System.out.println("PAGENYMUMUMU" + pageNumber);
        page.setIssue(issueTitle);
        page.setImgurl(imgurl);
        page.setPublished(false);
        page.setSeries(seriesTitle);
        page.setVotes(0);
        comicPageService.createPage(page);
        System.out.println("here");
        model.addAttribute("profileID", username);
        model.addAttribute("seriesTitle", seriesTitle);
        model.addAttribute("issueTitle", issueTitle);
        Member member = service.findMember(username);
        Series series = comicSeriesService.findSeriesByTitle(member.getUsername(),seriesTitle);
        Issue issue = issueService.findIssueByTitle(member.getUsername(), seriesTitle, issueTitle);
        int pageCount = issue.getPagecount()+1;
        issueService.updatePageCount(member.getUsername(), seriesTitle, issueTitle, pageCount);
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
