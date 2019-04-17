package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Series;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.comicsqueeze.comicsqueeze.service.ComicSeriesService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/createSeries")
public class createSeriesController {

    @Autowired
    private ComicSeriesService service;

    @RequestMapping(method=RequestMethod.POST, produces= "application/json")
    public JSONObject createSeries(@RequestBody Series newSeries, Model model, HttpSession session)
    {
        JSONObject message = new JSONObject();
        newSeries.setUsername((String)session.getAttribute("username"));
        newSeries.setTimestamp(LocalDateTime.now());
        System.out.println(newSeries.getUsername());
        System.out.println(newSeries.getTitle());
        System.out.println(newSeries.getDescription());
        System.out.println(newSeries.getTags());
        if (service.findSeriesByTitle(newSeries.getUsername(), newSeries.getTitle()) == null)
        {
            service.createSeries(newSeries);
            message.put("status", "OK");
        }
        else
        {
            message.put("status", "error");
        }
        return message;
    }
}
