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
    public String home(Model model, @RequestParam("userName") String userName)
    {
        model.addAttribute("userName",userName);
        return "CurrentUserProfile";
    }


}