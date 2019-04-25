package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.ComicIssueService;
import com.comicsqueeze.comicsqueeze.service.ComicPageService;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.comicsqueeze.comicsqueeze.service.ComicSeriesService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
public class publishIssueController {

    @Autowired
    private loginRegisterService service;

    @Autowired
    private ComicIssueService issueService;

    @Autowired
    private ComicPageService pageService;

    @Autowired
    private ComicSeriesService seriesService;

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public String controllerMethod(@RequestParam(value="publishedArray[]") List<Boolean> publishedArray, HttpSession session){
        Member member = (Member)session.getAttribute("curMember");
        System.out.println(member.getUsername());
        Issue issue = issueService.findIssueByTitle(member.getUsername(), member.getCurrentSeries().getTitle(), member.getCurrentIssue().getTitle());
        issue.setPages(pageService.queryAllPages(member, member.getCurrentSeries().getTitle(), issue.getTitle()));
        Collections.sort(issue.getPages());
        for (int i = 0;i < issue.getPages().size();i++)
        {
            issue.getPages().get(i).setPublished(publishedArray.get(i));
        }

        pageService.updatePages(issue.getPages());
        return "redirect:/yourprofile";
    }
}
