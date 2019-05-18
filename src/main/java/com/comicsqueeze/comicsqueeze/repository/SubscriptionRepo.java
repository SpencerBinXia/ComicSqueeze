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
        try
        {
            jdbc.update("INSERT INTO \"Subscription\"(subscriber,seriestitle,seriescreator)"
                    + "VALUES(?,?,?)", subscriber, seriesTitle, seriesCreator);
        }
        catch (Exception e)
        {

        }
    }

    public void deleteSubscription(String subscriber, String seriesTitle, String seriesCreator)
    {
        String deleteSubscription = "DELETE FROM \"Subscription\" WHERE subscriber= ? AND seriestitle= ? AND seriescreator= ?;";
        jdbc.update(deleteSubscription, subscriber, seriesTitle, seriesCreator);
    }

    public Subscription findSubscription(String subscriber, String seriesTitle, String seriesCreator)
    {
        String subscribeQuery ="SELECT * FROM \"Subscription\" WHERE subscriber= ? AND seriestitle= ? AND seriescreator= ?;";
        Subscription tempSubscript = new Subscription();
        try
        {
            jdbc.queryForObject(subscribeQuery, new Object[] {subscriber, seriesTitle, seriesCreator}, new RowMapper<Subscription>() {
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

    public void increaseFollows(String seriesCreator){
        String incFollowsQuery = "UPDATE \"Member\" SET follows = follows + 1 WHERE username='" + seriesCreator + "';";
        jdbc.update(incFollowsQuery);
    }

    public void decreaseFollows(String seriesCreator){
        String decFollowsQuery = "UPDATE \"Member\" SET follows = follows - 1 WHERE username='" + seriesCreator + "';";
        jdbc.update(decFollowsQuery);
    }

    public int sumSeriesSubscriptions(String seriesTitle, String seriesCreator)
    {
        String sumSeriesSubQuery = "SELECT COUNT(*) FROM \"Subscription\" WHERE seriestitle= ? AND seriescreator= ?;";
        System.out.println(sumSeriesSubQuery);

        try
        {
            int sum = jdbc.queryForObject(sumSeriesSubQuery, Integer.class, seriesTitle, seriesCreator);
            System.out.println(sum);
            return sum;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public int sumUserSubscriptions(String username)
    {
        String sumSeriesSubQuery = "SELECT COUNT(*) FROM \"Subscription\" WHERE seriescreator= ?;";

        try
        {
            int sum = jdbc.queryForObject(sumSeriesSubQuery, Integer.class, username);
            return sum;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public ArrayList<Subscription> queryAllSubscriptions(String subscriber)
    {
        String findSubscriptionQuery = "SELECT * FROM \"Subscription\" WHERE subscriber='" + subscriber + "';";
        List<Map<String, Object>> rows = jdbc.queryForList(findSubscriptionQuery);
        ArrayList<Subscription> subList = new ArrayList<>();
        for (Map rs : rows) {
            Subscription tempSub = new Subscription();
            tempSub.setSubscriber((String)rs.get("subscriber"));
            tempSub.setSeriesTitle((String)rs.get("seriestitle"));
            tempSub.setSeriesCreator((String)rs.get("seriescreator"));
            subList.add(tempSub);
        }
        return subList;
    }
}
