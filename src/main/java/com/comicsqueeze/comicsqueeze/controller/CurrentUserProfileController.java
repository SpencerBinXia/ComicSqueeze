package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;

@Controller

public class CurrentUserProfileController {

    @Autowired
    private loginRegisterService service;

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
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        return "CurrentUserProfile";
    }

    @RequestMapping(value="/updateImg", method=RequestMethod.GET)
    public String currentProf(Model model, HttpSession session,@RequestParam(value ="img", defaultValue = "images/icons/default_pro_icon.png") String imgURL)
    {
        Member curMember = service.findMember((String)session.getAttribute("username"));
        service.setImgURl(curMember,imgURL);
        curMember.setImgUrl(imgURL);
        model.addAttribute("curMember", curMember);
        return "CurrentUserProfile";
    }


}
