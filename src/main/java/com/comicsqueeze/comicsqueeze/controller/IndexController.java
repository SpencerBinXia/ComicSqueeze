package com.comicsqueeze.comicsqueeze.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class IndexController {
    @RequestMapping(value ="/",method = RequestMethod.GET)
    public String home(Model model, @RequestParam(value ="userName", defaultValue = "USERNAME") String userName )
    {
        boolean isAnon;
        if(userName.equals("USERNAME")){
            isAnon = true;
        } else {
            isAnon = false;
        }
        model.addAttribute("userName",userName);
        model.addAttribute("isAnon", isAnon);
        return "FrontPage";
    }

}
