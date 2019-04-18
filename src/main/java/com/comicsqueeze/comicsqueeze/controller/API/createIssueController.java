package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.comicsqueeze.comicsqueeze.service.ComicIssueService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/createIssue")
public class createIssueController {

    @Autowired
    private ComicIssueService service;

    @RequestMapping(method=RequestMethod.POST, produces= "application/json")
    public JSONObject createIssue(@RequestBody Issue newIssue, Model model, HttpSession session)
    {
        JSONObject message = new JSONObject();
        newIssue.setUsername((String)session.getAttribute("username"));
        Member member = (Member) session.getAttribute("curMember");
        newIssue.setSeries(member.getCurrentSeries().getTitle());
        if (service.findIssueByTitle(newIssue.getUsername(), newIssue.getSeries(), newIssue.getTitle()) == null)
        {
            service.createIssue(newIssue);
            message.put("username", newIssue.getUsername());
            message.put("issueTitle", newIssue.getTitle());
            message.put("seriesTitle", newIssue.getSeries());
            message.put("status", "OK");
        }
        else
        {
            message.put("status", "error");
        }
        return message;
    }
}
