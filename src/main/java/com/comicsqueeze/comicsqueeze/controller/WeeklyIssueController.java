package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("ALL")
@RestController


public class WeeklyIssueController {
    @Autowired
    private WeeklyContributionService weeklyContributionService;
    @RequestMapping("/createWeeklyIssue")
    public void createNewWeeklyIssue(@RequestParam String issueTitle, @RequestParam String description){
        weeklyContributionService.createNewWeeklyIssue(issueTitle,description);

    }

}
