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

    //public getReviews();


}
