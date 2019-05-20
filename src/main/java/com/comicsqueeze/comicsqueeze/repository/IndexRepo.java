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
        String findTopUsers = "SELECT * FROM \"Member\" GROUP BY username ORDER BY follows DESC fetch first 10 rows only;";
        ArrayList<Member> members = new ArrayList();
        try {
            List<Map<String,Object>> rows = jdbc.queryForList(findTopUsers);
            for(Map rs : rows){
                Member tempMember = new Member();
                tempMember.setUsername((String)rs.get("username"));
                tempMember.setImgUrl((String)rs.get("imgurl"));
                tempMember.setFollows((Integer)rs.get("follows"));
                members.add(tempMember);
            }
        }
        catch (Exception e) {
            return null;
        }
        return members;
    }

    public ArrayList<Issue> queryRecentIssues(){
        String findIssues = "SELECT * FROM \"Issue\" ORDER BY lastmodified DESC fetch first 20 rows only;";
        List<Map<String, Object>> rows = jdbc.queryForList(findIssues);
        ArrayList<Issue> issues = new ArrayList<>();
        try
        {
            for (Map rs : rows) {
                Issue tempIssue = new Issue();
                tempIssue.setTitle((String)rs.get("title"));
                tempIssue.setSeries((String)rs.get("series"));
                tempIssue.setDescription((String)rs.get("description"));
                tempIssue.setPagecount((Integer)rs.get("pagecount"));
                tempIssue.setUsername((String)rs.get("username"));
                tempIssue.setImgurl((String)rs.get("imgurl"));
                issues.add(tempIssue);
            }

        }
        catch (Exception e)
        {
            return null;
        }
        return issues;
    }

}
