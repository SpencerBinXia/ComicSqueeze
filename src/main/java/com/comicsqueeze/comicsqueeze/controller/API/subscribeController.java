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

@Controller
public class subscribeController {

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
        return "redirect:/";
    }

}
