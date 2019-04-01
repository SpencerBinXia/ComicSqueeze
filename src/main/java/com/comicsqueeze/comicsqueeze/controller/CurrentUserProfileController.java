package com.comicsqueeze.comicsqueeze.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/currentprof")
public class CurrentUserProfileController {

    @GetMapping
    public String home(Model model)
    {
        return "CurrentUserProfile";
    }

}
