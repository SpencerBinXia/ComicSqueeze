package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Series;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.comicsqueeze.comicsqueeze.service.loginRegisterService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/createSeries")
public class createSeriesController {

    @RequestMapping(method=RequestMethod.POST, produces= "application/json")
    public JSONObject createSeries(@RequestBody Series newSeries, Model model, HttpSession session)
    {
        newSeries.setUsername((String)session.getAttribute("username"));
        System.out.println(newSeries.getUsername());
        System.out.println(newSeries.getTitle());
        System.out.println(newSeries.getDescription());
        System.out.println(newSeries.getTags());
        JSONObject message = new JSONObject();
        message.put("status", "OK");
        return message;
    }
}
