package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.repository.SearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class SearchService {

    //ArrayList<Series> tempSeriesList = new ArrayList<>();

    @Autowired
    private SearchRepo searchRepo;

    public ArrayList<Series> queryAllSeriesByTitle(String searchString){
        ArrayList<Series> existing = searchRepo.searchAllSeriesByTitle(searchString);
        if(existing.size() == 0){ return null; }
        else{
            return existing;
        }
    }

    public ArrayList<Series> searchForMatchingTags(String searchString){
        ArrayList<Series> existing = searchRepo.searchForMatchingTags(searchString);
        if(existing.size() ==0 ){
            return null;
        }
        else{
            //tempSeriesList.addAll(existing);
            //Series stopSeries = new Series();
            //stopSeries.setTitle("stophere");
            //tempSeriesList.add(stopSeries);
            //System.out.println(tempSeriesList);
            return existing;
        }
    }

    public Member searchForUsername(String searchString){
        System.out.println("Search string in search service " + searchString);
        Member existing = searchRepo.searchForUsername(searchString);
        //System.out.println("member exist " + existing.getUsername() );
        if(existing == null){ return null; }
        else{
            System.out.println("if user exists in searchservice " + existing.getUsername());
            return existing;
        }
    }

    public ArrayList<String> deepKeywordSearch(String searchString){
        ArrayList<String> thequery = searchRepo.deepSearch(searchString);
        return thequery;
    }

    /*
    public ArrayList<Series> applyRatingHigh(){
        //Series hello = (Series) resultSeries;
        //System.out.println(resultSeries);
        ArrayList<Series> applyFilterToThese = new ArrayList<>();
        for(int i =0; i<tempSeriesList.size(); i++){
            if(tempSeriesList.get(i).getTitle().equals("stophere")){
                break;
            }
            else{
                applyFilterToThese.add(tempSeriesList.get(i));
                System.out.println(tempSeriesList.get(i).getTitle());
            }
        }
        return applyFilterToThese;

    }
*/
}
