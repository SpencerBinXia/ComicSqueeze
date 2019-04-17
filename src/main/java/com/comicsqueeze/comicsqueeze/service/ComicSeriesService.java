package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.repository.SeriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesService {
    @Autowired
    private SeriesRepo seriesRepo;
    public Series findSeriesByTitle(Member member, String seriesTitle){
        return seriesRepo.findBySeriesName(member, seriesTitle);
    }
    public void createSeries(Series newSeries){
        seriesRepo.createSeries(newSeries);
    }
}