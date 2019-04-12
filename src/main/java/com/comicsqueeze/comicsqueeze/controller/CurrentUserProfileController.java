package com.comicsqueeze.comicsqueeze.controller;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.awt.*;

@Controller

public class CurrentUserProfileController {
    @RequestMapping(value ="/signin", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, @RequestParam(value ="userName", defaultValue = "USERNAME") String userName, @RequestParam(value ="img", defaultValue = "images/icons/default_pro_icon.png") String imgURL )
    {
        System.out.println("yourprofile func called");
        model.addAttribute("userName",userName);
        model.addAttribute("img",imgURL);
        session.setAttribute("username", userName);
        return "redirect:/yourprofile";
    }

    @RequestMapping(value="/yourprofile", method=RequestMethod.GET)
    public String currentProf(Model model, HttpSession session)
    {
        return "CurrentUserProfile";
    }



}
