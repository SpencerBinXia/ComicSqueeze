package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.RateReview;

import com.comicsqueeze.comicsqueeze.object.Subscription;
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
public class SubscriptionRepo {

    @Autowired
    JdbcTemplate jdbc;

    public void insertSubscription(String subscriber, String seriesTitle, String seriesCreator){
        jdbc.update("INSERT INTO \"Subscription\"(subscriber,seriestitle,seriescreator)"
                        + "VALUES(?,?,?)",subscriber,seriesTitle,seriesCreator);
    }

    public void deleteSubscription(String subscriber, String seriesTitle, String seriesCreator)
    {
        String deleteSubscription = "DELETE FROM \"Subscription\" WHERE subscriber='" + subscriber + "' AND seriestitle='" + seriesTitle +
                                    "' AND seriescreator='" + seriesCreator + "';";
        jdbc.update(deleteSubscription);
    }

    public Subscription findSubscription(String subscriber, String seriesTitle, String seriesCreator)
    {
        String subscribeQuery ="SELECT * FROM \"Subscription\" WHERE subscriber='" + subscriber + "' AND seriestitle='" + seriesTitle +
                "' AND seriescreator='" + seriesCreator + "';";
        Subscription tempSubscript = new Subscription();
        try
        {
            jdbc.queryForObject(subscribeQuery, new RowMapper<Subscription>() {
                public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempSubscript.setSubscriber(rs.getString("subscriber"));
                    tempSubscript.setSeriesCreator(rs.getString("seriescreator"));
                    tempSubscript.setSeriesTitle(rs.getString("seriestitle"));
                    return tempSubscript;
                }
            });
        }
        catch (Exception e)
        {
            return null;
        }
        return tempSubscript;
    }

    public int sumSeriesSubscriptions(String seriesTitle, String seriesCreator)
    {
        String sumSeriesSubQuery = "SELECT COUNT(*) FROM \"Subscription\" WHERE seriestitle='" + seriesTitle + "' AND seriescreator='" + seriesCreator +
                "';";
        System.out.println(sumSeriesSubQuery);

        try
        {
            int sum = jdbc.queryForObject(sumSeriesSubQuery, Integer.class);
            System.out.println(sum);
            return sum;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}
