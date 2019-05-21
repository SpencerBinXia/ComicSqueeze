package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.RateReview;

import javax.servlet.http.HttpSession;
import com.comicsqueeze.comicsqueeze.repository.ProfileRepo;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.repository.RateReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RateReviewService {

    @Autowired
    RateReviewRepo rateRepo;

    public boolean createReview(RateReview newReview) {
        RateReview existing = rateRepo.findReview(newReview.getRater(), newReview.getSeriesTitle(), newReview.getSeriesCreator());
        if (existing == null) {
            System.out.println("null review reached");
            rateRepo.createReview(newReview);
            return true;
        }
        else
        {
            System.out.println("existing review reached");
            if (newReview.getReview().equals(""))
            {
                rateRepo.changeRating(newReview);
            }
            else
            {
                rateRepo.changeReview(newReview);
            }
            return true;
        }
    }

    public RateReview findReview(String rater, String seriesTitle, String seriesCreator){
        RateReview existing = rateRepo.findReview(rater, seriesTitle, seriesCreator);
        if (existing == null)
        {
            return null;
        }
        else
        {
            return existing;
        }
    }

    public double averageReview(String seriesTitle, String seriesCreator)
    {
        double average = rateRepo.queryAverageReview(seriesTitle, seriesCreator);
        return average;
    }

    public int countReview(String seriesTitle, String seriesCreator)
    {
        int count = rateRepo.queryCountReview(seriesTitle, seriesCreator);
        return count;
    }

    public ArrayList<RateReview> findAllReviewsFromSeries (String seriesTitle, String seriesCreator){
        return rateRepo.queryAllReviews(seriesTitle, seriesCreator);
    }

    public void deleteRatings(String seriesTitle, String seriesCreator)
    {
        rateRepo.deleteRatingsFromSeries(seriesTitle, seriesCreator);
    }
}
