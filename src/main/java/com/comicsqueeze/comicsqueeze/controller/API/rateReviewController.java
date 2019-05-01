package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.RateReview;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
public class rateReviewController {

    @Autowired
    private loginRegisterService service;

    @Autowired
    private ComicIssueService issueService;

    @Autowired
    private ComicPageService pageService;

    @Autowired
    private ComicSeriesService seriesService;

    @Autowired
    private RateReviewService rateService;

    @RequestMapping(value = "/ratereview", method = RequestMethod.POST)
    public String controllerMethod(@RequestParam(value="userRating") double userRating, Model model, HttpSession session){
        System.out.println(userRating);
        Member curMember = (Member) session.getAttribute("curMember");
        RateReview review = new RateReview();
        review.setRater(curMember.getUsername());
        review.setRating(userRating);
        review.setReview("");
        review.setSeriesTitle(curMember.getCurrentSeries().getTitle());
        review.setSeriesCreator(curMember.getCurrentSeries().getUsername());
        rateService.createReview(review);
        return "redirect:/";
    }
}
