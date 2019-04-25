package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.repository.SeriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
            System.out.println(existing.getTitle());
            return existing;
        }
    }
    public void createSeries(Series newSeries){
        seriesRepo.createSeries(newSeries);
    }

    public void deleteSeries(String series, String username) { seriesRepo.deleteSeries(series, username); }

    public void editSeries(String series, String username, String description, String tags){
        seriesRepo.updateSeries(series, username, description, tags);
    }
    public ArrayList<Series> queryAllSeries (Member member){
        return seriesRepo.queryAllSeries(member);
    }
}
