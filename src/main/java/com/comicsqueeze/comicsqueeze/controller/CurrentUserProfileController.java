package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.*;
import com.comicsqueeze.comicsqueeze.service.*;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

@Controller

public class CurrentUserProfileController {

    @Autowired
    private loginRegisterService service;
    @Autowired
    private ComicSeriesService comicSeriesService;
    @Autowired
    private ComicIssueService comicIssueService;
    @Autowired
    private SubscriptionService subService;
    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value ="/signin", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, @RequestParam(value ="userName", defaultValue = "USERNAME") String userName, @RequestParam(value ="img", defaultValue = "images/icons/default_pro_icon.png") String imgURL )
    {
        System.out.println("yourprofile func called");
        model.addAttribute("userName",userName);
        model.addAttribute("img",imgURL);
        Member curMember = service.findMember(userName);
        session.setAttribute("username", userName);
        model.addAttribute("curMember", curMember);

        session.setAttribute("curMember", curMember);
        return "redirect:/yourprofile";
    }

    @RequestMapping(value="/yourprofile", method=RequestMethod.GET)
    public String currentProf(Model model, HttpSession session)
    {
        if ((String)session.getAttribute("username") == null)
        {
            return "redirect:/";
        }
       int subscriptionCount = 0;
       Member curMember = service.findMember((String)session.getAttribute("username"));
       System.out.println(curMember.getUsername());
       model.addAttribute("curMember", curMember);
        //load all the series associated with the member
        ArrayList<Series> seriesArrayList = comicSeriesService.queryAllSeries(curMember);
        ArrayList<Series> groupSeriesList = comicSeriesService.queryforGroupSeries(curMember);
        //load each issue from the user series
        for(int i = 0; i < seriesArrayList.size(); i++){
            seriesArrayList.get(i).setIssueArrayList(comicIssueService.queryAllIssuesFromASeries(curMember, seriesArrayList.get(i)));
        }
        ArrayList<Subscription> subArrayList = subService.queryAllSubscriptions(curMember.getUsername());
        subscriptionCount = subService.sumUserSubscriptions((String)session.getAttribute("username"));
        model.addAttribute("subscriptions", subArrayList);
        model.addAttribute("subscriptionCount", subscriptionCount);
        model.addAttribute("groupSeries", groupSeriesList);
        //set the series to member variable to be loaded in app
        Member sessionMember = (Member) session.getAttribute("curMember");
        if(!(seriesArrayList.isEmpty())) {
            sessionMember.setSeriesArrayList(seriesArrayList);
        }
        else
        {
            sessionMember.setSeriesArrayList(null);
        }
        ArrayList<Notification> notifs = new ArrayList<>();
        //load all the notifications for a user
        if (sessionMember.getAdminStatus()==false) {
            notifs = notificationService.queryAllNotifications(sessionMember.getUsername());
        }
        if (sessionMember.getAdminStatus()){
           notifs= notificationService.queryAdminNotifs();
        }
        session.setAttribute("notifs",notifs);

        return "CurrentUserProfile";
    }


    /*
        function to update image
     */
    @RequestMapping(value="/updateImg", method=RequestMethod.GET)
    public String currentProf(Model model, HttpSession session,@RequestParam(value ="img", defaultValue = "images/icons/default_pro_icon.png") String imgURL)
    {
        // get the cur member and update image then use thymleafe to access image
        Member curMember = service.findMember((String)session.getAttribute("username"));
        service.setImgURl(curMember,imgURL);
        curMember.setImgUrl(imgURL);
        model.addAttribute("curMember", curMember);
        return "CurrentUserProfile";
    }


}
