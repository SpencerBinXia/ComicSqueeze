package com.comicsqueeze.comicsqueeze.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller

public class LogOutController {
    @RequestMapping(value ="/logout",method = RequestMethod.GET)
    public String home(Model model, HttpSession session, @RequestParam(value ="userName", defaultValue = "USERNAME") String userName, @RequestParam(value ="img", defaultValue = "images/icons/default_pro_icon.png") String imgURL )
    {
        System.out.println("logout controller");
//        model.addAttribute("userName",userName);
//        model.addAttribute("img",imgURL);
        Enumeration attrs =  session.getAttributeNames();
        while(attrs.hasMoreElements()) {
            session.removeAttribute((String)attrs.nextElement());
        }
        return "redirect:/";
    }
//
//    @RequestMapping(value="/yourprofile", method=RequestMethod.GET)
//    public String currentProf(Model model, HttpSession session)
//    {
//        return "CurrentUserProfile";
//    }

}
