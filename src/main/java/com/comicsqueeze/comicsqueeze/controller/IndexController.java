package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.object.WeeklyComic;
import com.comicsqueeze.comicsqueeze.service.*;
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
import java.util.TimeZone;

@Controller

public class IndexController {

    @Autowired
    private loginRegisterService service;
    @Autowired
    private ComicIssueService issueService;
    @Autowired
    private WeeklyContributionService weeklyContributionService;
    @Autowired
    private IndexService indexService;

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
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        cal.setTimeZone(tz);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        ArrayList<Page> weeklyContributions;

        // Top artists
        ArrayList<Member> top = indexService.queryTopArtists();
        model.addAttribute("topArtists", top);
        // Recent Issues
        ArrayList<Issue> recentIssues = indexService.queryRecentIssues();
        model.addAttribute("recentIssues", recentIssues);

        //Voting stops at 12 A.M EST
        int hours= cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        System.out.println(minutes);
        System.out.println(hours);
        // it is 12 A.M s

//        if ((hours==14)&& (minutes==0)){
//            // calculates max votes of day then sets the page with max votes to published true in weekly pages (meaning it is published in the issue for this weekly comic)
//            Page maxVotes = weeklyContributionService.calculateBestPage(thisWeekIssue,dayOfWeek);
//            if(maxVotes!=null) {
//                weeklyContributionService.addMaxVotesToSeries(maxVotes);
//            }
//            // reset everyones voted boolean its the end of the day
//            weeklyContributionService.setResetAllVoted();
//        }

        // HERE I QUERY FOR ALL THE CONTRIBUTIONS MADE FOR THIS WEEk FOR THIS DAY AND ADD IT TO THE MODEL
        weeklyContributions = weeklyContributionService.queryAllContributions(thisWeekIssue,dayOfWeek);
        // checks if a member already created a page for this issue today
        if(weeklyContributions!=null) {
            if (member != null) {
                member.setCurrentSeries(null);
                System.out.println(member.getVoted());
                if (weeklyContributionService.checkIfCreatedPage(member.getUsername(), thisWeekIssue, dayOfWeek)) {
                    member.setCreatedWeekly(true);
                }

            }
        }
        //HERE I Query for all the issues that were created for the weekly comic series
        ArrayList<Issue> weeklyissues = weeklyContributionService.getWeeklyIssues();
        for(Issue issue: weeklyissues){
            issue.setPages(weeklyContributionService.queryAllIssuePages(issue.getTitle()));
        }
        model.addAttribute("weeklyIssues",weeklyissues);
        model.addAttribute("weeklyContributions",weeklyContributions);
        return "FrontPage";
    }

}
