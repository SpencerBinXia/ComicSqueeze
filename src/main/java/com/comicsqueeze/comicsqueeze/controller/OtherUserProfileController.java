package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import com.comicsqueeze.comicsqueeze.service.ComicSeriesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/viewprofile/{profileID}")
public class OtherUserProfileController {

    @Autowired
    private loginRegisterService service;

    @Autowired
    private ComicSeriesService comicService;
    @GetMapping
    public String displayProfile(@PathVariable("profileID") String profileID, Model model, HttpSession session)
    {
        Member curMember = service.findMember((String)session.getAttribute("username"));
        System.out.println(profileID);
        Member displayMember = service.findMember(profileID);
        model.addAttribute("curMember", curMember);
        ArrayList<Series> seriesArrayList = comicService.queryAllSeries(displayMember);
        displayMember.setSeriesArrayList(seriesArrayList);
        System.out.println(displayMember.getUsername());
        model.addAttribute("displayMember", displayMember);
        if (profileID.equals(session.getAttribute("username")))
        {
            return "redirect:/yourprofile";
        }
        else if (service.findMember(profileID) == null)
        {
            return "redirect:/";
        }
        else
        {
            model.addAttribute("profileID", profileID);
            Member viewMember = service.findMember(profileID);
            model.addAttribute("viewMember", viewMember);
            return "OtherUserProfile";
        }
    }

}
