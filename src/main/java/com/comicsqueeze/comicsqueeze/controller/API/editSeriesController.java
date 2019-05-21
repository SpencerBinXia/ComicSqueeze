package com.comicsqueeze.comicsqueeze.controller.API;


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
public class editSeriesController {

    @Autowired
    private ComicSeriesService service;

    @RequestMapping(value = "/editSeries", method = RequestMethod.GET)
    public String editSeries(Model model, @RequestParam(value = "description") String description, @RequestParam(value = "tags") String tags, @RequestParam(value = "creators") String creators, HttpSession session){
        Member curMember = (Member)session.getAttribute("curMember");
        if (creators != null)
        {
            String[] creatorArray = creators.split(",", -1);
            System.out.println(creatorArray);
        }
        else
        {

        }
        service.editSeries(curMember.getCurrentSeries().getTitle(), curMember.getUsername(), description, tags);
        return "redirect:/series/" + curMember.getUsername() + "/" + curMember.getCurrentSeries().getTitle();
    }

}
