package com.comicsqueeze.comicsqueeze.controller.API;


import com.comicsqueeze.comicsqueeze.service.ComicPageService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.comicsqueeze.comicsqueeze.object.Page;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/createPage")
public class createPageController {

    @Autowired
    private ComicPageService service;
    @RequestMapping(method= RequestMethod.POST, produces= "application/json")
    public JSONObject createPage(@RequestBody Page newPage, Model model, HttpSession session){

        JSONObject message = new JSONObject();
        newPage.setUsername((String)session.getAttribute("username"));
        newPage.setPagenumber(1);
        System.out.println(newPage.getUsername());
        System.out.println(newPage.getPagenumber());
        System.out.println(newPage.getImgurl());
        if (service.findPageByNumber(newPage.getUsername(), newPage.getPagenumber(), newPage.getSeries(), newPage.getIssue()) == null)
        {
            service.createPage(newPage);
            message.put("status", "OK");
        }
        else
        {
            System.out.println("not null in controller");
            message.put("status", "error");
        }
        return message;

    }

}
