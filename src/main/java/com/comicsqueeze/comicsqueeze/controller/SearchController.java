package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.repository.RateReviewRepo;
import com.comicsqueeze.comicsqueeze.service.SubscriptionService;
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
    private SubscriptionService subService;
    @Autowired
    private RateReviewRepo reviewRepo;

    @GetMapping
    public String home(Model model, HttpSession session)
    {
        System.out.println("search controller called");
        Member curMember = service.findMember((String)session.getAttribute("username"));
        if(curMember != null){
            System.out.println("CurMember no null");
            model.addAttribute("curMember", curMember);
        }
        if(session.getAttribute("searchResults") != null){
            ArrayList<Series> ser = (ArrayList<Series>) session.getAttribute("searchResults");
            model.addAttribute("theResult", ser);
            session.setAttribute("searchResults", null);

            //Organize series list into hashmaps of series as key, rating as value
            //Sort by rating low
            Map<Series,Double> seriesLow = sortByRatingLow(ser);
            System.out.println("Rating Low to High:");
            Set set2 = seriesLow.entrySet();
            printMap(set2.iterator());
            model.addAttribute("seriesRatingLow", seriesLow);

            //Sort by rating high
            Map<Series,Double> seriesHigh = sortByRatingHigh(ser);
            Set set1 = seriesHigh.entrySet();
            System.out.println("Rating High to LOW");
            printMap(set1.iterator());
            model.addAttribute("seriesRatingHigh", seriesHigh);

            //Sort by recent
            for(Map.Entry<Series,Double> seri : seriesLow.entrySet()){
                System.out.println("Series and its timestamp");
                System.out.println(seri.getKey().getTitle());
                System.out.println(seri.getKey().getTimestamp());
            }
            for(int i =0; i < ser.size(); i++){
                System.out.println("series timestamp");
                System.out.println(ser.get(i).getTitle());
                System.out.println(ser.get(i).getTimestamp());
            }
            ArrayList<Series> seriesByRecent = ser;
            System.out.println("Series before sorted by recent");
            for(int i =0; i < seriesByRecent.size(); i++){
                System.out.println("series timestamp");
                System.out.println(ser.get(i).getTitle());
                System.out.println(ser.get(i).getTimestamp());
            }
            Collections.sort(seriesByRecent, new Comparator<Series>() {
                @Override
                public int compare(Series o1, Series o2) {
                    return o1.getTimestamp().compareTo(o2.getTimestamp());
                }
            });
            System.out.println("Series after sorted by recent");
            for(int i =0; i < seriesByRecent.size(); i++){
                System.out.println("series timestamp");
                System.out.println(ser.get(i).getTitle());
                System.out.println(ser.get(i).getTimestamp());
            }



        }
        else if(session.getAttribute("searchMemberResults") != null){
            ArrayList<Member> mem = (ArrayList<Member>) session.getAttribute("searchMemberResults");
            model.addAttribute("memResult", mem);
            session.setAttribute("searchMemberResults", null);

            ArrayList<Member> popularMembers = mem;
            /*
            for(int i =0; i<popularMembers.size(); i++){
                popularMembers.get(i).setFollows(subService.sumUserSubscriptions(popularMembers.get(i).getUsername()));
                //System.out.println(popularMembers.get(i).);
            }
            Collections.sort(popularMembers, new Comparator<Member>() {
                @Override
                public int compare(Member o1, Member o2) {
                    return o2.getFollows().compareTo(o1.getFollows());
                }
            }); */

        }


        return "Search";
    }

    private void printMap(Iterator iterator){
        while(iterator.hasNext()) {
            Map.Entry me2 = (Map.Entry)iterator.next();
            System.out.print(me2.getKey() + ": ");
            System.out.println(me2.getValue());
        }
    }
    private HashMap<Series,Double> sortByRatingHigh(ArrayList<Series> series){
        return sortByValuesReverse(storeSeriesAndRating(series));
    }

    private HashMap<Series,Double> sortByRatingLow(ArrayList<Series> series){
        return sortByValues(storeSeriesAndRating(series));
        //return sortByValues(seriesLow);
    }

    private HashMap<Series,Double> storeSeriesAndRating(ArrayList<Series> series){
        HashMap<Series,Double> seriesSort = new HashMap();
        for(int i =0; i < series.size(); i++){
            seriesSort.put(series.get(i),reviewRepo.queryAverageReview(series.get(i).getTitle(), series.get(i).getUsername()));
        }
        return seriesSort;
    }


    private static HashMap copyListToHashMap(List list, HashMap sortedHashMap){
        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        //HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
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
        return copyListToHashMap(list, new LinkedHashMap());
    }

    private static HashMap sortByValuesReverse(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        return copyListToHashMap(list, new LinkedHashMap());
    }

/*
    private HashMap<Series,Double> reverseMapOrder(Map<Series,Double> seriesRatingMap){
        Map<Series,Double> sortedMap = new LinkedHashMap<>();
        seriesRatingMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .forEachOrdered(entry ->
                        sortedMap.put(entry.getKey(), entry.getValue()));
    }
*/





}
