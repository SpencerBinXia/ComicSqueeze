package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class deleteSeriesController {

    @Autowired
    private loginRegisterService service;

    @Autowired
    private ComicIssueService issueService;

    @Autowired
    private ComicPageService pageService;

    @Autowired
    private ComicSeriesService seriesService;

    @Autowired
    private SubscriptionService subService;

    @Autowired
    private RateReviewService rateService;

    @RequestMapping(value ="/deleteSeries", method = RequestMethod.GET)
    public String deleteSeries(Model model, HttpSession session, @RequestParam(value = "seriesOwner") String username, @RequestParam(value = "seriesTitle") String seriesTitle)
    {
        Member member = (Member) session.getAttribute("curMember");
        if(member.getAdminStatus()){
            pageService.deleteSeriesPages(seriesTitle, username);
            subService.removeSubsFromSeries(seriesTitle, username);
            rateService.deleteRatings(seriesTitle, username);
            issueService.deleteIssues(seriesTitle, username);
            seriesService.deleteSeries(seriesTitle, username);
        }
        else{
            pageService.deleteSeriesPages(member.getCurrentSeries().getTitle(), member.getUsername());
            subService.removeSubsFromSeries(member.getCurrentSeries().getTitle(), member.getCurrentSeries().getUsername());
            rateService.deleteRatings(member.getCurrentSeries().getTitle(), member.getCurrentSeries().getUsername());
            issueService.deleteIssues(member.getCurrentSeries().getTitle(), member.getUsername());
            seriesService.deleteSeries(member.getCurrentSeries().getTitle(), member.getUsername());
        }
        return "redirect:/yourprofile";
    }
}
