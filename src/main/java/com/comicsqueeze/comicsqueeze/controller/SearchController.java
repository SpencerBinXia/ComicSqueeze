package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private loginRegisterService service;

    @GetMapping
    public String home(Model model, HttpSession session)
    {
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        if(session.getAttribute("searchResults") != null){
            ArrayList<Series> ser = (ArrayList<Series>) session.getAttribute("searchResults");
            model.addAttribute("theResult", ser);
            session.setAttribute("searchResults", null);
            System.out.println(ser.get(0));
        }
        else if(session.getAttribute("searchMemberResults") != null){
            Member mem = (Member) session.getAttribute("searchMemberResults");
            model.addAttribute("theResult", mem);
            session.setAttribute("searchMemberResults", null);
            System.out.println("yoyoyoy " + mem.getUsername());
        }
        //model.addAttribute("searchResult", model);

        return "Search";
    }


}
