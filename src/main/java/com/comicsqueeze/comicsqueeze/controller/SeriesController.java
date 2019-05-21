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
import java.util.ArrayList;

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
        int countRating;
        boolean subscribed = false;
        Series series;
        System.out.println("series controller" + seriesTitle);
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
               series = comicSeriesService.findSeriesByTitle(member.getUsername(), seriesTitle);
               series.setIssueArrayList(issueService.queryAllIssuesFromASeries(curMember, series));
               member.setCurrentSeries(series);
               model.addAttribute("currentSeries", series);
               model.addAttribute("seriesIssues", series.getIssueArrayList());
               model.addAttribute("seriesDesc", series.getDescription());
               model.addAttribute("seriesImg", series.getImgUrl());
           }
           else {
               Member displayMember = service.findMember(profileID);
               series = comicSeriesService.findSeriesByTitle(profileID, seriesTitle);
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
               model.addAttribute("seriesImg", series.getImgUrl());
           }
        }
        else
        {
            Member displayMember = service.findMember(profileID);
            series = comicSeriesService.findSeriesByTitle(profileID, seriesTitle);
            series.setIssueArrayList(issueService.queryAllIssuesFromASeries(displayMember, series));
            model.addAttribute("currentSeries", series);
            model.addAttribute("seriesIssues", series.getIssueArrayList());
            model.addAttribute("seriesDesc", series.getDescription());
            model.addAttribute("seriesImg", series.getImgUrl());
        }
        averageRating = rateService.averageReview(seriesTitle, profileID);
        countRating = rateService.countReview(seriesTitle, profileID);
        ArrayList<RateReview> reviewList = rateService.findAllReviewsFromSeries(seriesTitle, profileID);
        model.addAttribute("userRating", defaultRating);
        model.addAttribute("subscribed", subscribed);
        model.addAttribute("reviewList", reviewList);
        if (averageRating != -1.0)
        {
            model.addAttribute("averageRating", averageRating);
        }
        else
        {
            model.addAttribute("averageRating", "No ratings yet!");
        }
        if (countRating != -1)
        {
            model.addAttribute("countRating", countRating);
        }
        else
        {
            model.addAttribute("countRating", "Be the first to rate this series.");
        }
        if (series.isCollaborative())
        {
            if (!series.getCreators().equals("default"))
            {
                String[] creatorArray = series.getCreators().split(",", -1);
                System.out.println(creatorArray);
                model.addAttribute("creatorArray", creatorArray);
            }
        }

        return "SeriesPage";
    }
}
