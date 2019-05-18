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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class subscribeController {

    @Autowired
    loginRegisterService memberService;

    @Autowired
    ComicSeriesService seriesService;

    @Autowired
    SubscriptionService subService;

    @RequestMapping(value="/subscribe", method=RequestMethod.GET)
    public String subscribeMethod(Model model, HttpSession session)
    {
        Member curMember = (Member) session.getAttribute("curMember");
        System.out.println(curMember.getUsername());
        System.out.println(curMember.getCurrentSeries().getUsername());
        System.out.println(curMember.getCurrentSeries().getTitle());
        subService.insertSubscription(curMember.getUsername(), curMember.getCurrentSeries().getTitle(), curMember.getCurrentSeries().getUsername());
//        subService.increaseFollows(curMember.getCurrentSeries().getUsername());
        return "redirect:/";
    }

    @RequestMapping(value="/unsubscribe", method=RequestMethod.GET)
    public String unsubscribeMethod(Model model, HttpSession session)
    {
        Member curMember = (Member) session.getAttribute("curMember");
        System.out.println(curMember.getUsername());
        System.out.println(curMember.getCurrentSeries().getUsername());
        System.out.println(curMember.getCurrentSeries().getTitle());
        subService.removeSubscription(curMember.getUsername(), curMember.getCurrentSeries().getTitle(), curMember.getCurrentSeries().getUsername());
//        subService.decreaseFollows(curMember.getCurrentSeries().getUsername());
        return "redirect:/";
    }

    @RequestMapping(value="/subscribeAll", method=RequestMethod.POST)
    public String subscribeAllMethod(@RequestParam(value="displayName") String displayName, Model model, HttpSession session)
    {
        String username = (String)session.getAttribute("username");
        System.out.println(username + displayName);
        ArrayList<Series> seriesArrayList = seriesService.queryAllSeries(memberService.findMember(displayName));
        for(int i = 0; i < seriesArrayList.size(); i++){
            subService.insertSubscription(username, seriesArrayList.get(i).getTitle(), displayName);
//            subService.increaseFollows(displayName);
        }
        return "redirect:/";
    }

    @RequestMapping(value="/unsubscribeAll", method=RequestMethod.POST)
    public String unsubscribeAllMethod(@RequestParam(value="displayName") String displayName, Model model, HttpSession session)
    {
        String username = (String)session.getAttribute("username");
        System.out.println(username + displayName);
        ArrayList<Series> seriesArrayList = seriesService.queryAllSeries(memberService.findMember(displayName));
        for(int i = 0; i < seriesArrayList.size(); i++){
            subService.removeSubscription(username, seriesArrayList.get(i).getTitle(), displayName);
//            subService.decreaseFollows(displayName);
        }
        return "redirect:/";
    }

}
