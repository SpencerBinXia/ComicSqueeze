package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.ComicIssueService;
import com.comicsqueeze.comicsqueeze.service.ComicPageService;
import com.comicsqueeze.comicsqueeze.service.ComicSeriesService;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/issue/{profileID}/{seriesTitle}/{issueTitle}")
public class IssueController {
    @Autowired
    private loginRegisterService service;
    @Autowired
    private ComicIssueService issueService;
    @Autowired
    private ComicSeriesService comicSeriesService;
    @Autowired
    private ComicPageService comicPageService;
    @GetMapping
    public String displayProfile(HttpServletRequest request, @PathVariable("profileID") String profileID, @PathVariable("seriesTitle") String seriesTitle, @PathVariable("issueTitle") String issueTitle, Model model, HttpSession session)
    {
        System.out.println(request.getRequestURI());
        model.addAttribute("profileID", profileID);
        model.addAttribute("seriesTitle", seriesTitle);
        model.addAttribute("issueTitle", issueTitle);
        Member member = service.findMember(profileID);
        Series series = comicSeriesService.findSeriesByTitle(member.getUsername(),seriesTitle);
        if (series.isCollaborative())
        {
            List creatorList = Arrays.asList(series.getCreators().split(","));
           for (int i = 0;i < creatorList.size();i++)
           {
               if (creatorList.get(i).equals((String)session.getAttribute("username")))
               {
                   System.out.println("Creator found");
                   model.addAttribute("creator", true);
               }
           }
           model.addAttribute("collaborative", true);
           model.addAttribute("creatorList", creatorList);
        }
        Issue issue = issueService.findIssueByTitle(member.getUsername(), seriesTitle, issueTitle);
        issue.setPages(comicPageService.queryAllPages(member, seriesTitle, issueTitle));
        Collections.sort(issue.getPages());
        int p = 1;
        for (int i = 0;i < issue.getPages().size();i++)
        {
            System.out.println("issueControllerforLoop");
            System.out.println(issue.getPages().get(i).getPagenumber());
            issue.getPages().get(i).setPageArrayNumber(i+1);
            if (issue.getPages().get(i).isPublished())
            {
                issue.getPages().get(i).setPublishedArrayNumber(p);
                p++;
            }
        }
        ArrayList<Page> pages =  issue.getPages();
        model.addAttribute("issue", issue);
        model.addAttribute("pages",pages);

        if(member.getUsername().equals((String)session.getAttribute("username"))) {
            member = (Member) session.getAttribute("curMember");
            model.addAttribute("curMember", member);
            member.setCurrentSeries(series);
            member.setCurrentIssue(issue);
            session.setAttribute("curMember", member);
        }
        else if (session.getAttribute("username") != null)
        {
            member = (Member) session.getAttribute("curMember");
            model.addAttribute("curMember", (Member)session.getAttribute("curMember"));
            member.setCurrentSeries(series);
            member.setCurrentIssue(issue);
            session.setAttribute("curMember", member);

        }
        return "IssuePage";
    }

}
