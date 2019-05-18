package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.repository.IndexRepo;
import com.comicsqueeze.comicsqueeze.repository.SeriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IndexService {

    @Autowired
    private IndexRepo indexRepo;

    public ArrayList<Member> queryTopArtists(){
        return indexRepo.queryTopArtists();
    }

}
