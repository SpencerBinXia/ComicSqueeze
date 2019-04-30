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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchRepo {
    @Autowired
    JdbcTemplate jdbc;

    public ArrayList<Series> searchAllSeriesByTitle(String searchString) {
        System.out.println("Matching series titles with " + searchString);
        String findSeries = "SELECT * FROM \"Series\" WHERE seriestitle ='" + searchString + "';";
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
            tempSeries.setTimestamp((LocalDateTime)(rs.get("time_stamp")));
            tempSeries.setRateCounter((int)rs.get("ratecounter"));
            series.add(tempSeries);
        }
        return series;
    }

    public ArrayList<Series> searchForMatchingTags(String searchString){
        System.out.println("Matching Tags with" + searchString);
        String findTagsFromSeries = "SELECT * FROM \"Series\" WHERE " + "find_in_set(" + searchString + ",tags)" + "';";
        List<Map<String, Object>> rows = jdbc.queryForList(findTagsFromSeries);
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
            tempSeries.setTimestamp((LocalDateTime)(rs.get("time_stamp")));
            tempSeries.setRateCounter((int)rs.get("ratecounter"));
            series.add(tempSeries);
        }
        return series;
    }

    public Member searchForUsername(String searchString){
        System.out.println("Matching users with" + searchString);
        String findUser = "SELECT * FROM \"Member\" WHERE username" + searchString + "';";
        Member tempMember = new Member();
        try
        {
            jdbc.queryForObject(findUser, new RowMapper<Member>() {
                public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempMember.setUsername(rs.getString("username"));
                    tempMember.setBio(rs.getString("bio"));
                    tempMember.setAdminStatus(rs.getBoolean("admin"));
                    tempMember.setEmail(rs.getString("email"));
                    tempMember.setImgUrl(rs.getString("imgurl"));
                    return tempMember;
                }
            });
        }
        catch (Exception e)
        {
            return null;
        }
        return tempMember;
    }

}
