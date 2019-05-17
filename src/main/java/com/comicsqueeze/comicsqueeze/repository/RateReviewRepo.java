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

    public void changeRating(RateReview review)
    {
        System.out.println("inside changereview" + review.getRating() + review.getRater() + review.getSeriesTitle() + review.getSeriesCreator());
        String changeReviewQuery ="UPDATE ratereview SET rating= ? WHERE rater= ? AND seriestitle= ? AND seriescreator= ?;";
        jdbc.update(changeReviewQuery, review.getRating(), review.getRater(), review.getSeriesTitle(), review.getSeriesCreator());
    }

    public void changeReview(RateReview review)
    {
        System.out.println("inside changereview" + review.getRating() + review.getRater() + review.getSeriesTitle() + review.getSeriesCreator());
        String changeReviewQuery ="UPDATE ratereview SET rating= ?,review = ? WHERE rater= ? AND seriestitle= ? AND seriescreator= ?;";
        jdbc.update(changeReviewQuery, review.getRating(), review.getReview(), review.getRater(), review.getSeriesTitle(), review.getSeriesCreator());
    }

    public double queryAverageReview(String seriesTitle, String seriesCreator)
    {
        String averageQuery = "SELECT AVG (rating) FROM ratereview WHERE seriestitle= ? AND seriescreator= ?;";

        try
        {
            return jdbc.queryForObject(averageQuery, Double.class, seriesTitle, seriesCreator);
        }
        catch (Exception e)
        {
            return -1.0;
        }
    }

    public RateReview findReview(String currentUser, String seriesTitle, String seriesCreator){
        String reviewQuery ="SELECT * FROM ratereview WHERE rater= ? AND seriestitle= ? AND seriescreator= ?;";
        System.out.println("inside findreview" + reviewQuery);
        RateReview tempRate = new RateReview();
        try
        {
            jdbc.queryForObject(reviewQuery, new Object[] {currentUser, seriesTitle, seriesCreator}, new RowMapper<RateReview>() {
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

    public ArrayList<RateReview> queryAllReviews(String seriesTitle, String seriesCreator) {
        String findSeries = "SELECT * FROM ratereview WHERE seriestitle= ? AND seriescreator= ?;";
        List<Map<String, Object>> rows = jdbc.queryForList(findSeries, seriesTitle, seriesCreator);
        ArrayList<RateReview> reviews = new ArrayList<>();
        for (Map rs : rows) {
            RateReview tempReview = new RateReview();
            tempReview.setRater((String)rs.get("rater"));
            tempReview.setSeriesTitle((String)rs.get("seriestitle"));
            tempReview.setSeriesCreator((String)rs.get("seriescreator"));
            tempReview.setRating((double)rs.get("rating"));
            tempReview.setReview((String)rs.get("review"));
            if (!tempReview.getReview().equals(""))
            {
                reviews.add(tempReview);
            }
        }
        return reviews;
    }
    //public getReviews();


}
