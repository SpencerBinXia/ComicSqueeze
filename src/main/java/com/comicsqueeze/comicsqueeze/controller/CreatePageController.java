package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.*;
import com.comicsqueeze.comicsqueeze.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;

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
    @Autowired
    private SubscriptionService subService;
    @Autowired
    private WeeklyContributionService weeklyContributionService;
    @RequestMapping("/createpage")
    public String home(Model model, HttpSession session)
    {
        //Member curMember = service.findMember((String)session.getAttribute("username"));
        Member curMember = (Member) session.getAttribute("curMember");
        model.addAttribute("curMember", curMember);
        return "CreatePage";
    }

    @RequestMapping("/pageDB")
    public ModelAndView addPageToDB( HttpSession session, @RequestParam("username") String username, @RequestParam("seriesTitle") String seriesTitle, @RequestParam("issueTitle") String issueTitle, @RequestParam("pageNumber") int pageNumber, @RequestParam("imgurl") String imgurl,@RequestParam(value = "custom", defaultValue = "false") Boolean custom)
    {
        ModelAndView model = new ModelAndView("IssuePage");
        System.out.println("pageDB username: " + username + ",issueTitle: " + issueTitle);
        Page page = new Page();
        page.setUsername(username);
        page.setPagenumber(pageNumber);
        page.setIssue(issueTitle);
        page.setImgurl(imgurl);
        page.setPublished(false);
        page.setSeries(seriesTitle);
        page.setCustom(custom);
        page.setCreator((String)session.getAttribute("username"));
        page.setVotes(0);
        comicPageService.createPage(page);
        model.addObject("profileID", username);
        model.addObject("seriesTitle", seriesTitle);
        model.addObject("issueTitle", issueTitle);
        Member member = service.findMember(username);
        Series series = comicSeriesService.findSeriesByTitle(member.getUsername(),seriesTitle);
        Issue issue = issueService.findIssueByTitle(member.getUsername(), seriesTitle, issueTitle);
        int pageCount = issue.getPagecount()+1;
        issueService.updatePageCount(member.getUsername(), seriesTitle, issueTitle, pageCount);
        issue.setPages(comicPageService.queryAllPages(member, seriesTitle, issueTitle));
        ArrayList<Page> pages =  issue.getPages();
        model.addObject("issue", issue);
        model.addObject("pages",pages);

        if(member.getUsername().equals((String)session.getAttribute("username"))) {
            member = (Member) session.getAttribute("curMember");
            model.addObject("curMember", member);
            member.setCurrentSeries(series);
            member.setCurrentIssue(issue);
            session.setAttribute("curMember", member);
        }
        else if (session.getAttribute("username") != null)
        {
            model.addObject("curMember", (Member)session.getAttribute("curMember"));
        }
        return model;
    }
    @RequestMapping("/editPageDB")
    public ModelAndView editPageToDB( HttpSession session, @RequestParam("username") String username, @RequestParam("seriesTitle") String seriesTitle, @RequestParam("issueTitle") String issueTitle, @RequestParam("pageNumber") int pageNumber, @RequestParam("imgurl") String imgurl)
    {
        ModelAndView model = new ModelAndView("IssuePage");
        Page page = new Page();
        page.setUsername(username);
        page.setPagenumber(pageNumber);
        page.setIssue(issueTitle);
        page.setImgurl(imgurl);
        page.setPublished(false);
        page.setSeries(seriesTitle);
        page.setVotes(0);
        comicPageService.editPage(page);
        model.addObject("profileID", username);
        model.addObject("seriesTitle", seriesTitle);
        model.addObject("issueTitle", issueTitle);
        Member member = service.findMember(username);
        Series series = comicSeriesService.findSeriesByTitle(member.getUsername(),seriesTitle);
        Issue issue = issueService.findIssueByTitle(member.getUsername(), seriesTitle, issueTitle);
        issue.setPages(comicPageService.queryAllPages(member, seriesTitle, issueTitle));
        ArrayList<Page> pages =  issue.getPages();
        model.addObject("issue", issue);
        model.addObject("pages",pages);

        if(member.getUsername().equals((String)session.getAttribute("username"))) {
            member = (Member) session.getAttribute("curMember");
            model.addObject("curMember", member);
            member.setCurrentSeries(series);
            member.setCurrentIssue(issue);
            session.setAttribute("curMember", member);
        }
        else if (session.getAttribute("username") != null)
        {
            model.addObject("curMember", (Member)session.getAttribute("curMember"));
        }
        return model;
    }
    @RequestMapping("/weeklyPageDB")
    public String addWeeklyPageToDB( Model model,HttpSession session, @RequestParam("username") String username, @RequestParam("seriesTitle") String seriesTitle, @RequestParam("issueTitle") String issueTitle, @RequestParam("pageNumber") int pageNumber, @RequestParam("imgurl") String imgurl)
    {
        //Add page to weeklyDB

        Page page = new Page();
        page.setUsername(username);
        page.setPagenumber(pageNumber);
        page.setIssue(issueTitle);
        page.setImgurl(imgurl);
        page.setPublished(false);
        page.setSeries(seriesTitle);
        page.setVotes(0);
         Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        comicPageService.createWeeklyPage(page,dayOfWeek);
        //update the current page count for this weekly issue
        weeklyContributionService.updatePageCount(issueTitle, pageNumber+1);
        //add this user to this weekly issue
        weeklyContributionService.addContributor(issueTitle,username);
        Member curMember = (Member) session.getAttribute("curMember");
        // set the currentSeries of the user to null incase he decides to create a page on weekly comic
        if(curMember!=null) {
            curMember.setCurrentSeries(null);
        }
        model.addAttribute("curMember", curMember);
        model.addAttribute("userName",username);
        curMember.setCreatedWeekly(true);

        return "FrontPage";
    }
    @RequestMapping("/seriesCoverDB")
    public String addSeriesCoverToDB( Model model,HttpSession session, @RequestParam("username") String username, @RequestParam("seriesTitle") String seriesTitle,@RequestParam("imgurl") String imgurl)
    {
        comicSeriesService.addSeriesCover(username,seriesTitle,imgurl);
        subService.updateSubImage(seriesTitle, username, imgurl);
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        return "CreateSeriesCover";

    }
    @RequestMapping("/issueCoverDB")
    public String addIssueCoverToDB( Model model,HttpSession session, @RequestParam("username") String username, @RequestParam("seriesTitle") String seriesTitle,@RequestParam("issueTitle") String issueTitle, @RequestParam("imgurl") String imgurl)
    {
        issueService.addIssueCover(username,seriesTitle,issueTitle,imgurl);
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        return "CreateIssueCover";

    }

}
