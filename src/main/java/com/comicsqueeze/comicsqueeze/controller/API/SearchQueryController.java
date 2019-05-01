package com.comicsqueeze.comicsqueeze.controller.API;


import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
public class SearchQueryController {


    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/Search", method = RequestMethod.GET)
    public String searchQuery(Model model, @RequestParam(value = "searchString") String searchString, @RequestParam(value = "filter") String filter, HttpSession session){
        Member member = (Member) session.getAttribute("curMember");
        if(filter.equals("tags")){
            ArrayList<Series> series = searchService.searchForMatchingTags(searchString);
            if(series != null){
                return series.toString();
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

}
