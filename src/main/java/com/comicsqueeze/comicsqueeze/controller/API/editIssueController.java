package com.comicsqueeze.comicsqueeze.controller.API;


import com.comicsqueeze.comicsqueeze.service.ComicIssueService;
import com.comicsqueeze.comicsqueeze.service.ComicSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import com.comicsqueeze.comicsqueeze.object.Member;

@Controller
public class editIssueController {

    @Autowired
    private ComicIssueService service;

    @RequestMapping(value = "/editIssue", method = RequestMethod.GET)
    public String editSeries(Model model, @RequestParam(value = "description") String description, HttpSession session){
        Member curMember = (Member)session.getAttribute("curMember");
        service.editIssue(curMember.getUsername(), curMember.getCurrentSeries().getTitle(), curMember.getCurrentIssue().getTitle(), description);
        return "redirect:/issue/" + curMember.getUsername() + "/" + curMember.getCurrentSeries().getTitle() + "/" + curMember.getCurrentIssue().getTitle();
    }

}
