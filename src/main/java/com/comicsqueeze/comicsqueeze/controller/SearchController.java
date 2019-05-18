package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.repository.RateReviewRepo;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private loginRegisterService service;

    @Autowired
    private RateReviewRepo reviewRepo;

    @GetMapping
    public String home(Model model, HttpSession session)
    {
        System.out.println("search controller called");
        Member curMember = service.findMember((String)session.getAttribute("username"));
        model.addAttribute("curMember", curMember);
        if(session.getAttribute("searchResults") != null){
            ArrayList<Series> ser = (ArrayList<Series>) session.getAttribute("searchResults");
            model.addAttribute("theResult", ser);
            session.setAttribute("searchResults", null);

            System.out.println("ser: "+ ser);
            Map<Series,Double> seriesLow = sortByRatingLow(ser);
            System.out.println("seriesLow: "+ seriesLow);

            System.out.println("After Sorting:");
            Set set2 = seriesLow.entrySet();
            Iterator iterator2 = set2.iterator();
            while(iterator2.hasNext()) {
                Map.Entry me2 = (Map.Entry)iterator2.next();
                System.out.print(me2.getKey() + ": ");
                System.out.println(me2.getValue());
            }
            model.addAttribute("seriesSortedLow", seriesLow);







            //ArrayList<Series> seriesLow = sortByRatingLow(ser);
            //System.out.println(ser.get(0));
        }
        else if(session.getAttribute("searchMemberResults") != null){
            ArrayList<Member> mem = (ArrayList<Member>) session.getAttribute("searchMemberResults");
            model.addAttribute("memResult", mem);
            session.setAttribute("searchMemberResults", null);
        }
        return "Search";
    }

    private HashMap<Series,Double> sortByRatingLow(ArrayList<Series> series){
        HashMap<Series,Double> seriesHigh = new HashMap();
        for(int i =0; i < series.size(); i++){
            seriesHigh.put(series.get(i),reviewRepo.queryAverageReview(series.get(i).getTitle(), series.get(i).getUsername()));
        }
        return sortByValues(seriesHigh);
    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }





}
