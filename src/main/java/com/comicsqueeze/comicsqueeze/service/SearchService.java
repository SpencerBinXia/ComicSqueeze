package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.repository.SearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SearchService {

    @Autowired
    private SearchRepo searchRepo;

    public ArrayList<Series> queryAllSeriesByTitle(String searchString){
        ArrayList<Series> existing = searchRepo.searchAllSeriesByTitle(searchString);
        if(existing == null){ return null; }
        else{
            System.out.println(existing.get(0));
            return existing;
        }
    }

    public ArrayList<Series> searchForMatchingTags(String searchString){
        ArrayList<Series> existing = searchRepo.searchForMatchingTags(searchString);
        if(existing == null){
            return null;
        }
        else{
            System.out.println(existing.get(0));
            return existing;
        }
    }

    public Member searchForUsername(String searchString){
        System.out.println("Search string in search service " + searchString);
        Member existing = searchRepo.searchForUsername(searchString);
        System.out.println("member exist " + existing.getUsername() );
        if(existing == null){ return null; }
        else{
            System.out.println("if user exists in searchservice " + existing.getUsername());
            return existing;
        }
    }
}
