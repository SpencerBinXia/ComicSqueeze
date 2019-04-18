package com.comicsqueeze.comicsqueeze.repository;


import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Repository
public class SeriesRepo {
    @Autowired
    JdbcTemplate jdbc;

    public Series findBySeriesName(String username, String seriestitle){
        String findSeries = "SELECT * FROM \"Series\" WHERE username ='" + username + "' AND seriestitle='" + seriestitle + "';";
        Series tempSeries = new Series();
        try
        {
            jdbc.queryForObject(findSeries, new RowMapper<Series>() {
                public Series mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempSeries.setTitle(rs.getString("seriestitle"));
                    tempSeries.setDescription(rs.getString("description"));
                    tempSeries.setUsername(rs.getString("username"));
                    tempSeries.setCollaborative(rs.getBoolean("collaborative"));
                    tempSeries.setFlag(rs.getBoolean("flag"));
                    tempSeries.setRating(rs.getDouble("rating"));
                    tempSeries.setWeekly(rs.getBoolean("weekly"));
                    tempSeries.setTags(rs.getString("tags"));
                    tempSeries.setCreators(rs.getString("creators"));
                    tempSeries.setTimestamp(rs.getObject(5, LocalDateTime.class));
                    return tempSeries;
                }
            });
        }
        catch (Exception e)
        {
            return null;
        }
        return tempSeries;
    }

    public void createSeries(Series newSeries){
        jdbc.update("INSERT INTO \"Series\"(seriestitle,username,creators,tags,views,weekly,flag,timestamp,rating,collaborative,description)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?)", newSeries.getTitle(),newSeries.getUsername(),newSeries.getCreators(),newSeries.getTags(),
                newSeries.getViews(),newSeries.isWeekly(),newSeries.isFlag(),newSeries.getTimestamp(), newSeries.getRating(),newSeries.isCollaborative(),newSeries.getDescription());
    }
}
