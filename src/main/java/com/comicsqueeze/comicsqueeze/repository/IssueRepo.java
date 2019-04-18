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
import java.util.List;
import java.util.Map;

@Repository
public class IssueRepo {
    @Autowired
    JdbcTemplate jdbc;

    public Issue findByIssueTitle(String username, String seriestitle, String issuetitle){

        String findIssue = "SELECT * FROM \"Issue\" WHERE username='" + username + "' AND title='" + issuetitle + "' AND series='" +
                seriestitle + "';";
        Issue tempIssue = new Issue();
        try
        {
            jdbc.queryForObject(findIssue, new RowMapper<Issue>() {
                public Issue mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempIssue.setTitle(rs.getString("title"));
                    tempIssue.setDescription(rs.getString("description"));
                    tempIssue.setUsername(rs.getString("username"));
                    tempIssue.setSeries(rs.getString("series"));
                    tempIssue.setPagecount(rs.getInt("pagecount"));
                    tempIssue.setTimestamp(rs.getObject(5, LocalDateTime.class));
                    return tempIssue;
                }
            });
        }
        catch (Exception e)
        {
            return null;
        }
        return tempIssue;
    }
    public void createIssue(Issue newIssue){
        jdbc.update("INSERT INTO \"Issue\"(title,series,description,pagecount,timestamp,username)" + "VALUES(?,?,?,?,?,?)", newIssue.getTitle(),newIssue.getSeries(),
                newIssue.getDescription(),newIssue.getPagecount(),newIssue.getTimestamp(),newIssue.getUsername());
    }

    public ArrayList<Issue> queryAllIssuesFromASeries(Member member, Series series) {
        String findIssue = "SELECT * FROM \"Issue\" WHERE username ='" + member.getUsername() + "'AND series='" + series.getTitle() + "';";
        List<Map<String, Object>> rows = jdbc.queryForList(findIssue);
        ArrayList<Issue> issues = new ArrayList<>();
        for (Map rs : rows) {
            Issue tempIssue = new Issue();
            tempIssue.setTitle((String)rs.get("seriestitle"));
            tempIssue.setDescription((String)rs.get("description"));
            tempIssue.setUsername((String)rs.get("username"));
            tempIssue.setPagecount((Integer)rs.get("pagecount"));
            tempIssue.setTimestamp((LocalDateTime)rs.get("time_stamp"));
            tempIssue.setSeries((String)rs.get("issue"));
            issues.add(tempIssue);
        }
        return issues;
    }
}

