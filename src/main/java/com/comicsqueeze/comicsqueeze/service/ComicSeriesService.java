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
    public Series findSeriesByTitle(String username, String seriesTitle){
        Series existing = seriesRepo.findBySeriesName(username, seriesTitle);
        if (existing == null)
        {
            return null;
        }
        else
        {
            return existing;
        }
    }
    public void createSeries(Series newSeries){
        seriesRepo.createSeries(newSeries);
    }
}
