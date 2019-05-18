package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.object.WeeklyComic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class IndexRepo {

    @Autowired
    JdbcTemplate jdbc;

    public ArrayList<Member> queryTopArtists(){
        String findTopUsers = "SELECT TOP(10) FROM \"Member\" GROUP BY username ORDER BY follows DESC;";
        ArrayList<Member> members = new ArrayList();
        try
        {
            List<Map<String,Object>> rows = jdbc.queryForList(findTopUsers);
            for(Map rs : rows){
                Member tempMember = new Member();
                tempMember.setUsername((String)rs.get("username"));
                System.out.println("Value in searchForUsername in SearchRepo " + tempMember.getUsername());
                members.add(tempMember);
            }
        }
        catch (Exception e)
        {
            return null;
        }

        return members;
    }

}
