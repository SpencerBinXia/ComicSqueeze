package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/issue/{seriesTitle}/{issueTitle}")

public class WeeklyIssueController {
    @Autowired
    private WeeklyContributionService weeklyContributionService;
    @GetMapping
    public String displayProfile( @PathVariable("seriesTitle") String seriesTitle, @PathVariable("issueTitle") String issueTitle, Model model, HttpSession session)
    {

        model.addAttribute("seriesTitle", seriesTitle);
        model.addAttribute("issueTitle", issueTitle);
        Issue issue = weeklyContributionService.queryForIssue(issueTitle);
        issue.setPages(weeklyContributionService.queryAllIssuePages(issueTitle));
        Collections.sort(issue.getPages());
        int p = 1;
        for (int i = 0;i < issue.getPages().size();i++)
        {
            issue.getPages().get(i).setPageArrayNumber(i+1);
            if (issue.getPages().get(i).isPublished())
            {
                issue.getPages().get(i).setPublishedArrayNumber(p);
                p++;
            }
        }
        ArrayList<Page> pages =  issue.getPages();
        model.addAttribute("issue", issue);
        model.addAttribute("pages",pages);


        return "WeeklyIssuePages";
    }

}
