package com.comicsqueeze.comicsqueeze.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller

public class IndexController {
    @RequestMapping(value ="/",method = RequestMethod.GET)
    public String home(Model model, @RequestParam(value ="userName", defaultValue = "USERNAME") String userName, HttpSession session)
    {
        model.addAttribute("userName",userName);
        return "FrontPage";
    }

}
