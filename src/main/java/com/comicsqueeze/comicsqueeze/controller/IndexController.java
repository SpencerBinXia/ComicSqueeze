package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.object.WeeklyComic;
import com.comicsqueeze.comicsqueeze.service.ComicIssueService;
import com.comicsqueeze.comicsqueeze.service.ComicPageService;
import com.comicsqueeze.comicsqueeze.service.WeeklyContributionService;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;

@Controller

public class IndexController {

    @Autowired
    private loginRegisterService service;
    @Autowired
    private ComicIssueService issueService;
    @Autowired
    private WeeklyContributionService weeklyContributionService;

    @RequestMapping(value ="/",method = RequestMethod.GET)
    public String home(Model model, @RequestParam(value ="userName", defaultValue = "USERNAME") String userName, HttpSession session)
    {

        Member curMember = service.findMember((String)session.getAttribute("username"));
        // set the currentSeries of the user to null incase he decides to create a page on weekly comic
        Member member = (Member) session.getAttribute("curMember");

        String thisWeekIssue = issueService.queryForWeeklyIssue();
        WeeklyComic thisWeeklyComic = issueService.queryForWeeklyComic(thisWeekIssue);
        session.setAttribute("weeklyComic",thisWeeklyComic);
        model.addAttribute("curMember", curMember);
        model.addAttribute("userName",userName);
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        ArrayList<Page> weeklyContributions;
        // HERE I QUERY FOR ALL THE CONTRIBUTIONS MADE FOR THIS WEEk FOR THIS DAY AND ADD IT TO THE MODEL
        weeklyContributions = weeklyContributionService.queryAllContributions(thisWeekIssue,dayOfWeek);
        // checks if a member already created a page for this issue today
        if(member!=null) {
            member.setCurrentSeries(null);
            if(weeklyContributionService.checkIfCreatedPage(member.getUsername(),thisWeekIssue,dayOfWeek)){
                member.setCreatedWeekly(true);
            }
        }
        model.addAttribute("weeklyContributions",weeklyContributions);
        return "FrontPage";
    }

}
