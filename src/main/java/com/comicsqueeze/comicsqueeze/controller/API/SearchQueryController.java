package com.comicsqueeze.comicsqueeze.controller.API;


import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.IndexService;
import com.comicsqueeze.comicsqueeze.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.swing.plaf.metal.MetalMenuBarUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
public class SearchQueryController {


    @Autowired
    private SearchService searchService;
    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/SearchSeriesTitle", method = RequestMethod.GET)
    public ArrayList<Series> querySeriesTitle(Model model, @RequestParam(value = "searchString") String searchString, HttpSession session){
        if(!searchString.chars().allMatch(Character::isWhitespace)){
            ArrayList<Series> allSeriesResults = new ArrayList<>();
            List<String> splits = Arrays.asList(searchString.split(" "));
            for(int i =0; i <splits.size(); i++){
                if(!splits.get(i).isEmpty()){
                    ArrayList<Series> series = searchService.queryAllSeriesByTitle(splits.get(i));
                    if(series != null){
                        allSeriesResults.addAll(series);
                    }
                }
            }
            //ArrayList<Series> series = searchService.queryAllSeriesByTitle(searchString);
            if(allSeriesResults != null){
                /*System.out.println("Results with possible duplicates: ");
                for(int i = 0; i < allSeriesResults.size(); i++){
                    System.out.print("Series ");
                    System.out.print(allSeriesResults.get(i).getTitle());
                    System.out.println(allSeriesResults.get(i).getUsername());
                }*/
                ArrayList<Series> seri = removeDuplicateResultsSeries(allSeriesResults);
                session.setAttribute("searchResults", seri);
                //model.addAttribute("seriesTitleResults", series);
                //redirectAttributes.addFlashAttribute("searchResult", model);
                //session.setAttribute("seriesTitleResults", series);
                return seri;
            }
            return null;
        }
        else{
            System.out.println("search string empty");
            ArrayList<Series> series = searchService.returnAllSeries();
            if(series != null){
                session.setAttribute("searchResults", series);
                return series;
            }
        }
        return null;

    }
    private ArrayList<Series> removeDuplicateResultsSeries(ArrayList<Series> series){
        ArrayList<Series> del = new ArrayList<>();
        for(Series ser1 : series){
            boolean isFound =  false;
            for(Series ser2 : del){
                if(ser1 != ser2){
                    if((ser1.getTitle().compareTo(ser2.getTitle()) ==0) && (ser1.getUsername().compareTo(ser2.getUsername()) ==0)){
                        isFound = true;
                        break;
                    }
                }
            }
            if (!isFound) del.add(ser1);
        }
        return del;
    }
    private ArrayList<Member> removeDuplicateResultsMember(ArrayList<Member> members){
        ArrayList<Member> del = new ArrayList<>();
        for(Member mem1 : members){
            boolean isFound =  false;
            for(Member mem2 : del){
                if(mem1 != mem2){
                    if(mem1.getUsername().compareTo(mem2.getUsername()) ==0){
                        isFound = true;
                        break;
                    }
                }
            }
            if (!isFound) del.add(mem1);
        }
        return del;
    }
    @RequestMapping(value = "/SearchTags", method = RequestMethod.GET)
    public ArrayList<Series> queryTags(Model model, @RequestParam(value = "searchString") String searchString, HttpSession session){
        ArrayList<Series> allSeriesResults = new ArrayList<>();
        if(!searchString.chars().allMatch(Character::isWhitespace)){
            System.out.println("Search string has one nonwhitespace char");
            List<String> splits = Arrays.asList(searchString.split(" "));
            for(int i =0; i <splits.size(); i++){
                if(!splits.get(i).isEmpty()){
                    ArrayList<Series> series = searchService.searchForMatchingTags(splits.get(i));
                    if(series != null){
                        allSeriesResults.addAll(series);
                    }
                }
            }
            //System.out.println(splits.toString());
            //ArrayList<Series> series = searchService.searchForMatchingTags(searchString);
            System.out.println("got here 6");
            if(allSeriesResults != null){
                ArrayList<Series> allSeriesResultsNoDups = removeDuplicateResultsSeries(allSeriesResults);
                session.setAttribute("searchResults", allSeriesResultsNoDups);
                //model.addAttribute("tagResults", series);
                //redirectAttributes.addFlashAttribute("searchResult", model);
                //session.setAttribute("tagResults", series);
                return allSeriesResultsNoDups;
            }
            return null;
        }
        else{
            ArrayList<Series> series = searchService.returnAllSeries();
            System.out.println("search string empty");
            if(series != null){
                session.setAttribute("searchResults", series);
                return series;
            }
        }
        return null;
    }

    @RequestMapping(value = "/SearchUsername", method = RequestMethod.GET)
    public ArrayList<Member> queryUsername(Model model, @RequestParam(value = "searchString") String searchString, HttpSession session){
        ArrayList<Member> allMemberResults = new ArrayList<>();
        if(!searchString.chars().allMatch(Character::isWhitespace)){
            List<String> splits = Arrays.asList(searchString.split(" "));
            for(int i =0; i <splits.size(); i++){
                if(!splits.get(i).isEmpty()){
                    ArrayList<Member> members = searchService.searchForUsername(splits.get(i));
                    if(members != null){
                        allMemberResults.addAll(members);
                    }
                }
            }
            ArrayList<Member> members = searchService.searchForUsername(searchString);
            if(allMemberResults != null){
                ArrayList<Member> allMemberResultsUnique = removeDuplicateResultsMember(allMemberResults);
                session.setAttribute("searchMemberResults", allMemberResultsUnique);
                //model.addAttribute("usernameResult", member);
                //redirectAttributes.addFlashAttribute("searchResult", model);
                //session.setAttribute("usernameResult", member);
                return allMemberResultsUnique;
            }
            return null;
        }
        else{
            ArrayList<Member> members = indexService.queryTopArtists();
            if(members != null){
                session.setAttribute("searchMemberResults",members);
                return members;
            }
        }
        return null;
    }

    @RequestMapping(value = "/SearchKeywords", method = RequestMethod.GET)
    public ArrayList<String> queryKeywords(@RequestParam(value = "searchString") String searchString, HttpSession session){
        ArrayList<String> keys = searchService.deepKeywordSearch(searchString);
        if(!keys.isEmpty()){
            //session.setAttribute("");
            for(int i = 0; i<keys.size(); i++){
                System.out.println(keys.get(i));
            }
            return keys;
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
