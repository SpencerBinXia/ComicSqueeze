package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
public class WeeklyContributionRepo {
    @Autowired
    JdbcTemplate jdbc;

    public void updatePageCount(String issueTitle, int pageCount) {
        String updateWeekly= "UPDATE \"WeeklyComic\" SET PAGES= '"+ pageCount+"';";
        jdbc.update(updateWeekly);
    }

    /*
        This method adds a contributor to the list of contributors for this issue of the
        weekly comic, if the users is already int the lsit it wont add them again
     */
    public void addContributor(String issueTitle, String username) {
        String getUsers = "SELECT users FROM \"WeeklyComic\" WHERE issuetitle='"+issueTitle+"';";
         Issue issue = new Issue();
        try
        {
            jdbc.queryForObject(getUsers, new RowMapper<Issue>() {
                public Issue mapRow(ResultSet rs, int rowNum) throws SQLException {

                    issue.setTitle(rs.getString("users"));
                    return issue;
                }
            });
        }
        catch (Exception e)
        {

        }
        if (issue.getTitle()!=null) {
            if (!issue.getTitle().contains(username)) {
                String updateWeekly = "UPDATE \"WeeklyComic\" SET USERS= '" + issue.getTitle() + username + ",';";
                jdbc.update(updateWeekly);
            }
        }
        else{
            String updateWeekly = "UPDATE \"WeeklyComic\" SET USERS= '" +username + ",';";
            jdbc.update(updateWeekly);
        }
    }
}
