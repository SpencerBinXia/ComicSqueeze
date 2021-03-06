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
        if (!newSeries.getCreators().equals("default"))
        {
            String[] creatorArray = newSeries.getCreators().split(",", -1);
            System.out.println(creatorArray);
            newSeries.setCreatorArray(creatorArray);
        }
        System.out.println(newSeries.isCollaborative());
        System.out.println(newSeries.getCreators());
        System.out.println(newSeries.getUsername());
        System.out.println(newSeries.getTitle());
        System.out.println("DESCRIPTION: " + newSeries.getDescription());
        System.out.println(newSeries.getTags());
        if (service.findSeriesByTitle(newSeries.getUsername(), newSeries.getTitle()) == null)
        {
            service.createSeries(newSeries);
            message.put("username", newSeries.getUsername());
            message.put("seriesTitle", newSeries.getTitle());
            message.put("status", "OK");
        }
        else
        {
            message.put("status", "error");
        }
        return message;
    }
}
