package com.comicsqueeze.comicsqueeze.repository;


import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class SeriesRepo {
    @Autowired
    JdbcTemplate jdbc;

    public Series findBySeriesName(String username, String seriestitle){
        String findSeries = "SELECT * FROM \"Series\" WHERE username = ? AND seriestitle= ?;";
        System.out.println("findbyseries" + username);
        System.out.println("findbyseries" + seriestitle);
        Series tempSeries = new Series();
        try
        {
            jdbc.queryForObject(findSeries, new Object[] {username, seriestitle}, new RowMapper<Series>() {
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
                    tempSeries.setImgUrl( rs.getString("imgurl"));
                    Date tempDate = (rs.getObject(5, Date.class));
                    tempSeries.setTimestamp(LocalDateTime.ofInstant(tempDate.toInstant(), ZoneId.systemDefault()));
                    tempSeries.setRateCounter(rs.getInt("ratecounter"));
                    System.out.println("inside findseries query" + tempSeries.getTitle());
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
        jdbc.update("INSERT INTO \"Series\"(seriestitle,username,creators,tags,views,weekly,flag,timestamp,rating,collaborative,description,ratecounter)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", newSeries.getTitle(),newSeries.getUsername(),newSeries.getCreators(),newSeries.getTags(),
                newSeries.getViews(),newSeries.isWeekly(),newSeries.isFlag(),newSeries.getTimestamp(), newSeries.getRating(),newSeries.isCollaborative(),newSeries.getDescription(),newSeries.getRateCounter());
    }

    public ArrayList<Series> queryAllSeries(Member member) {
        System.out.println(member.getUsername());
        String findSeries = "SELECT * FROM \"Series\" WHERE username ='" + member.getUsername() + "';";
        List<Map<String, Object>> rows = jdbc.queryForList(findSeries);
        ArrayList<Series> series = new ArrayList<>();
        for (Map rs : rows) {
            Series tempSeries = new Series();
            tempSeries.setTitle((String)rs.get("seriestitle"));
            tempSeries.setDescription((String)rs.get("description"));
            tempSeries.setUsername((String)rs.get("username"));
            tempSeries.setCollaborative((boolean)rs.get("collaborative"));
            tempSeries.setFlag((boolean)rs.get("flag"));
            tempSeries.setRating((double)rs.get("rating"));
            tempSeries.setWeekly((boolean)rs.get("weekly"));
            tempSeries.setTags((String)rs.get("tags"));
            tempSeries.setCreators((String)rs.get("creators"));
            tempSeries.setImgUrl((String) rs.get("imgurl"));
            Date tempDate = ((Date)rs.get("timestamp"));
            tempSeries.setTimestamp(LocalDateTime.ofInstant(tempDate.toInstant(), ZoneId.systemDefault()));
            tempSeries.setRateCounter((int)rs.get("ratecounter"));
            series.add(tempSeries);
        }
        return series;
    }

    public void deleteSeries(String series, String username)
    {
        String deleteSeries = "DELETE FROM \"Series\" WHERE seriestitle= ? AND username= ?;";
        jdbc.update(deleteSeries, series, username);
    }

    public void updateSeries(String series, String username, String description, String tags){
        String updatedSeries = "UPDATE \"Series\" SET description= ?, tags= ? WHERE username= ? AND seriestitle= ?;";
        jdbc.update(updatedSeries, description, tags, username, series);
    }

    public void addSeriesCover(String username, String seriesTitle, String imgurl) {
        String updatedSeries = "UPDATE \"Series\" SET IMGURL='"+imgurl+"'WHERE username='"+username+"' AND seriestitle='"+seriesTitle+"';";
        jdbc.update(updatedSeries);
    }
}
