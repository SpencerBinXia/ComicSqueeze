package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.RateReview;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.object.Subscription;
import com.comicsqueeze.comicsqueeze.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/series/{profileID}/{seriesTitle}")
public class SeriesController {
    @Autowired
    private loginRegisterService service;
    @Autowired
    private ComicSeriesService comicSeriesService;
    @Autowired
    private ComicIssueService issueService;
    @Autowired
    private RateReviewService rateService;
    @Autowired
    private SubscriptionService subService;

    @GetMapping
    public String home(@PathVariable("profileID") String profileID, @PathVariable("seriesTitle") String seriesTitle, Model model, HttpSession session)
    {
        double defaultRating = 0.0;
        double averageRating = 0.0;
        boolean subscribed = false;
        int totalSubscriptions = subService.sumSeriesSubscriptions(seriesTitle, profileID);
        System.out.println("seriesController totalsub: " + totalSubscriptions);
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("profileID", profileID);
        model.addAttribute("seriesTitle", seriesTitle);
        model.addAttribute("totalSubscriptions", totalSubscriptions);
        //model.addAttribute("seriesDesc", curMember.getCurrentSeries().getDescription());
        if(curMember!=null) {
            model.addAttribute("curMember", curMember);
            Member member = (Member) session.getAttribute("curMember");
           if(profileID.equals(curMember.getUsername())) {
               Series series = comicSeriesService.findSeriesByTitle(member.getUsername(), seriesTitle);
               series.setIssueArrayList(issueService.queryAllIssuesFromASeries(curMember, series));
               member.setCurrentSeries(series);
               model.addAttribute("currentSeries", series);
               model.addAttribute("seriesIssues", series.getIssueArrayList());
               model.addAttribute("seriesDesc", series.getDescription());
           }
           else {
               Member displayMember = service.findMember(profileID);
               Series series = comicSeriesService.findSeriesByTitle(profileID, seriesTitle);
               series.setIssueArrayList(issueService.queryAllIssuesFromASeries(displayMember, series));
               member.setCurrentSeries(series);
               Subscription subscribedTo = subService.findSubscription(curMember.getUsername(), series.getTitle(), series.getUsername());
               if (subscribedTo != null)
               {
                   subscribed = true;
               }
               RateReview rating = rateService.findReview(member.getUsername(), series.getTitle(), series.getUsername());
               if (rating != null)
               {
                   defaultRating = rating.getRating();
               }
               model.addAttribute("currentSeries", series);
               model.addAttribute("seriesIssues", series.getIssueArrayList());
               model.addAttribute("seriesDesc", series.getDescription());
           }
        }
        else
        {
            Member displayMember = service.findMember(profileID);
            Series series = comicSeriesService.findSeriesByTitle(profileID, seriesTitle);
            series.setIssueArrayList(issueService.queryAllIssuesFromASeries(displayMember, series));
            model.addAttribute("currentSeries", series);
            model.addAttribute("seriesIssues", series.getIssueArrayList());
            model.addAttribute("seriesDesc", series.getDescription());
        }
        averageRating = rateService.averageReview(seriesTitle, profileID);
        model.addAttribute("userRating", defaultRating);
        model.addAttribute("subscribed", subscribed);
        if (averageRating != -1.0)
        {
            model.addAttribute("averageRating", averageRating);
        }
        else
        {
            model.addAttribute("averageRating", "No ratings yet!");
        }
        return "SeriesPage";
    }
}
