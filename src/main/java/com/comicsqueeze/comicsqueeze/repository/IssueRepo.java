package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
}

