package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DeletePageController {
    @RequestMapping("/deletePage/{username}/{seriesTitle}/{issueTitle}/{pageNumber}")
    public void home(@PathVariable("username") String username, @PathVariable("seriesTitle") String seriesTitle,@PathVariable("issueTitle") String issueTitle, @PathVariable("pageNumber") int pageNumber){
        Page p = new Page();
        p.setUsername(username);
        p.setSeries(seriesTitle);
        p.setIssue(issueTitle);
        p.setPagenumber(pageNumber);
    }
}
