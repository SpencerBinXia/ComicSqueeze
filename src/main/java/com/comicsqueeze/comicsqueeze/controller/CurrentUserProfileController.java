package com.comicsqueeze.comicsqueeze.controller;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;

@Controller

public class CurrentUserProfileController {
    @RequestMapping(value ="/currentprof",method = RequestMethod.GET)
    public String home(Model model, @RequestParam(value ="userName", defaultValue = "USERNAME") String userName,@RequestParam(value ="img", defaultValue = "images/icons/default_pro_icon.png") String imgURL )
    {
        boolean isAnon;
        if(userName.equals("USERNAME")){
            isAnon = true;
        } else {
            isAnon = false;
        }
        model.addAttribute("userName",userName);
        model.addAttribute("img",imgURL);
        model.addAttribute("isAnon", isAnon);
        return "CurrentUserProfile";
    }



}
