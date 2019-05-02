package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.RateReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RateReviewRepo {

    @Autowired
    JdbcTemplate jdbc;

    public void createReview(RateReview newReview)
    {
        jdbc.update("INSERT INTO ratereview(rater,seriestitle,seriescreator,rating,review)"
                        + "VALUES(?,?,?,?,?)", newReview.getRater(), newReview.getSeriesTitle(), newReview.getSeriesCreator(), newReview.getRating(),
                    newReview.getReview());
    }

    public void changeReview(RateReview review)
    {
        System.out.println("inside changereview" + review.getRating() + review.getRater() + review.getSeriesTitle() + review.getSeriesCreator());
        String changeReviewQuery ="UPDATE ratereview SET rating='" + review.getRating() + "' WHERE rater='" + review.getRater() +
                                  "' AND seriestitle='" + review.getSeriesTitle() + "' AND seriescreator='" + review.getSeriesCreator() + "';";
        jdbc.update(changeReviewQuery);
    }

    public double queryAverageReview(String seriesTitle, String seriesCreator)
    {
        String averageQuery = "SELECT AVG (rating) FROM ratereview WHERE seriestitle='" + seriesTitle + "' AND" +
                              " seriescreator='" + seriesCreator + "';";

        try
        {
            return jdbc.queryForObject(averageQuery, Double.class);
        }
        catch (Exception e)
        {
            return -1.0;
        }
    }

    public RateReview findReview(String currentUser, String seriesTitle, String seriesCreator){
        String reviewQuery ="SELECT * FROM ratereview WHERE rater='" + currentUser + "' AND seriestitle='" + seriesTitle +
                           "' AND seriescreator='" + seriesCreator + "';";
        System.out.println("inside findreview" + reviewQuery);
        RateReview tempRate = new RateReview();
        try
        {
            jdbc.queryForObject(reviewQuery, new RowMapper<RateReview>() {
                public RateReview mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempRate.setRater(rs.getString("rater"));
                    tempRate.setSeriesCreator(rs.getString("seriescreator"));
                    tempRate.setSeriesTitle(rs.getString("seriestitle"));
                    tempRate.setReview(rs.getString("review"));
                    tempRate.setRating(rs.getDouble("rating"));
                    System.out.println(tempRate.getRater() + tempRate.getRating());
                    return tempRate;
                }
            });
        }
        catch (Exception e)
        {
            return null;
        }
        return tempRate;
    }
    //public getReviews();


}