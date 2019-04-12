package com.comicsqueeze.comicsqueeze.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/viewprofile/{profileID}")
public class OtherUserProfileController {

    @GetMapping
    public String displayProfile(@PathVariable("profileID") String profileID, Model model, HttpSession session)
    {
        model.addAttribute("profileID", profileID);
        System.out.println(profileID);
        return "OtherUserProfile";
    }

}
