package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.service.WeeklyContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class voteController {
    @Autowired
    private WeeklyContributionService weeklyContributionService;
    @RequestMapping("/castVote")
    public String castVote(@RequestParam("username") String username, @RequestParam("issue") String issue,@RequestParam("pagenum") int pagenum){
        weeklyContributionService.castVote(issue,username,pagenum);
        return "redirect:/?userName="+username;
    }
}
