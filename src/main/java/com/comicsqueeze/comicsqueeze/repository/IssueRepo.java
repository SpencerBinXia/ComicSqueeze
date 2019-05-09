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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class IssueRepo {
    @Autowired
    JdbcTemplate jdbc;

    public Issue findByIssueTitle(String username, String seriestitle, String issuetitle){

        String findIssue = "SELECT * FROM \"Issue\" WHERE username= ? AND title = ? AND series = ?;";
        Issue tempIssue = new Issue();
        try
        {
            jdbc.queryForObject(findIssue, new Object[] {username, issuetitle, seriestitle}, new RowMapper<Issue>() {
                public Issue mapRow(ResultSet rs, int rowNum) throws SQLException {
                    System.out.println("issue result");
                    tempIssue.setTitle(rs.getString("title"));
                    tempIssue.setDescription(rs.getString("description"));
                    tempIssue.setUsername(rs.getString("username"));
                    tempIssue.setSeries(rs.getString("series"));
                    tempIssue.setPagecount(rs.getInt("pagecount"));
                    tempIssue.setTimestamp(rs.getObject(5, LocalDateTime.class));
                    System.out.println("before return issue result");
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
        String findIssue = "SELECT * FROM \"Issue\" WHERE username = ? and series = ?;";
        List<Map<String, Object>> rows = jdbc.queryForList(findIssue, member.getUsername(), series.getTitle());
        ArrayList<Issue> issues = new ArrayList<>();
        for (Map rs : rows) {
            Issue tempIssue = new Issue();
            tempIssue.setTitle((String)rs.get("title"));
            tempIssue.setDescription((String)rs.get("description"));
            tempIssue.setUsername((String)rs.get("username"));
            tempIssue.setPagecount((Integer)rs.get("pagecount"));
            tempIssue.setTimestamp((LocalDateTime)rs.get("time_stamp"));
            tempIssue.setSeries((String)rs.get("series"));
            issues.add(tempIssue);
        }
        return issues;
    }

    public void deleteIssue(String issueTitle, String series, String username)
    {
            String deleteIssue = "DELETE FROM \"Issue\" WHERE title= ? AND series = ? AND username = ?;";
            jdbc.update(deleteIssue, issueTitle, series, username);
    }

    public void deleteIssues(String series, String username)
    {
        String deleteIssue = "DELETE FROM \"Issue\" WHERE series= ? AND username = ?;";
        jdbc.update(deleteIssue, series, username);
    }

    public void updatePageCount(String username, String seriesTitle, String issueTitle, int pageCount) {
        String updateIssue= "UPDATE \"Issue\" SET PAGECOUNT= ? WHERE title= ? AND username= ?;";
        jdbc.update(updateIssue, pageCount, issueTitle, username);
    }

    public String queryforWeeklyIssue() {
        String findIssue = "SELECT * FROM \"WeeklyIssueTitle\" ;";
        Issue tempIssue = new Issue();
        try
        {

            jdbc.queryForObject(findIssue, new RowMapper<Issue>() {
                public Issue mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempIssue.setTitle(rs.getString("issueTitle"));
                return tempIssue;
                }
            });
        }
        catch (Exception e)
        {
            return null;
        }
        return tempIssue.getTitle();
    }
    public WeeklyComic queryforWeeklyIssue(String issuetitle) {
        String findComic = "SELECT * FROM \"WeeklyComic\" WHERE issuetitle= ?;";
                WeeklyComic weeklyComic = new WeeklyComic();
        try
        {

            jdbc.queryForObject(findComic, new Object[] {issuetitle}, new RowMapper<WeeklyComic>() {
                public WeeklyComic mapRow(ResultSet rs, int rowNum) throws SQLException {
                    weeklyComic.setIssueTitle(rs.getString("issueTitle"));
                    weeklyComic.setPages(rs.getString("pages"));
                    weeklyComic.setUsers(rs.getString("users"));
                    return weeklyComic;
                }
            });
        }
        catch (Exception e)
        {
            return null;
        }
        return weeklyComic;
    }
}

