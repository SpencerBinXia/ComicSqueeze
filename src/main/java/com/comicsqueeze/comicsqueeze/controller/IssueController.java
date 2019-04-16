package com.comicsqueeze.comicsqueeze.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/series/{profileID}/{seriesTitle}/{issueTitle}")
public class IssueController {

    @GetMapping
    public String home(@PathVariable("profileID") String profileID, @PathVariable("seriesTitle") String seriesTitle, @PathVariable("issueTitle") String issueTitle,  Model model, HttpSession session)
    {
        model.addAttribute("profileID", profileID);
        model.addAttribute("seriesTitle", seriesTitle);
        model.addAttribute("issueTitle", issueTitle);
        return "IssuePage";
    }

}
