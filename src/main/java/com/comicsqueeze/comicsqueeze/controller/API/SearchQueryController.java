package com.comicsqueeze.comicsqueeze.controller.API;


import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
public class SearchQueryController {


    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/SearchSeriesTitle", method = RequestMethod.GET)
    public ArrayList<Series> querySeriesTitle(Model model, @RequestParam(value = "searchString") String searchString, HttpSession session){
        ArrayList<Series> series = searchService.queryAllSeriesByTitle(searchString);
        if(series != null){
            session.setAttribute("searchResults", series);
            //model.addAttribute("seriesTitleResults", series);
            //redirectAttributes.addFlashAttribute("searchResult", model);
            //session.setAttribute("seriesTitleResults", series);
            return series;
        }
        return null;
    }

    @RequestMapping(value = "/SearchTags", method = RequestMethod.GET)
    public ArrayList<Series> queryTags(Model model, @RequestParam(value = "searchString") String searchString, HttpSession session){
        ArrayList<Series> series = searchService.searchForMatchingTags(searchString);
        if(series != null){
            session.setAttribute("searchResults", series);
            //model.addAttribute("tagResults", series);
            //redirectAttributes.addFlashAttribute("searchResult", model);
            //session.setAttribute("tagResults", series);
            return series;
        }
        return null;
    }

    @RequestMapping(value = "/SearchUsername", method = RequestMethod.GET)
    public Member queryUsername(Model model, @RequestParam(value = "searchString") String searchString, HttpSession session, final RedirectAttributes redirectAttributes){
        Member member = searchService.searchForUsername(searchString);
        if(member != null){
            session.setAttribute("searchMemberResults", member);
            //model.addAttribute("usernameResult", member);
            //redirectAttributes.addFlashAttribute("searchResult", model);
            //session.setAttribute("usernameResult", member);
            return member;
        }
        return null;
    }
/*
    @RequestMapping(value = "/sortByDescendingRating")
    public void filterRatingHigh(Model model, @RequestParam(value = "theResults") String theResults, HttpSession session){
        searchService.applyRatingHigh();
    }

    @RequestMapping(value = "/Search", method = RequestMethod.GET)
    public String searchQuery(Model model, @RequestParam(value = "searchString") String searchString, @RequestParam(value = "filter") String filter, HttpSession session){
        Member member = (Member) session.getAttribute("curMember");
        if(filter.equals("tags")){
            ArrayList<Series> series = searchService.searchForMatchingTags(searchString);
            if(series != null){
                return series;
            }
            return null;
        }
        else if(filter.equals("username")){
            Member foundMember = searchService.searchForUsername(searchString);
            if(foundMember != null){
                return foundMember.toString();
            }
            return null;
        }
        else if(filter.equals("seriestitle")){
            ArrayList<Series> series = searchService.queryAllSeriesByTitle(searchString);
            if(series != null){
                return series.toString();
            }
            return null;
        }
        else if(filter.equals("keywords")){
            return searchService.deepKeywordSearch(searchString).toString();
        }
        return "broo";
    }
*/

}
