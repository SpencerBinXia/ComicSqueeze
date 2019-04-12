package com.comicsqueeze.comicsqueeze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.comicsqueeze.comicsqueeze.service.loginRegisterService;

import javax.servlet.http.HttpSession;

@Controller
public class APIController {

    @Autowired
    private loginRegisterService service;

    @RequestMapping(value ="/updateBio", method = RequestMethod.GET)
    public String home(Model model, @RequestParam(value ="bio") String bio, HttpSession session)
        {
            service.setBio((String)session.getAttribute("username"), bio);
            return "redirect:/yourprofile";
        }
}
