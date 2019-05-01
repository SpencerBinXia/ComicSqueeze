package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.object.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
         I use the issue object to store the users in because I dont know how to just
        return one string with a query for some reasone it has to be stored
        in an object
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
    /*
        This method queries and returns all the contributions made by users site-wide
        for the currently weekly comic issue
     */
    public ArrayList<Page> queryAllContributions(String issueTitle) throws Exception {
      ArrayList<Page> contributions = new ArrayList<>();
      // get all the contributors for this issue
      String contributorsList = getContibutors(issueTitle);
      //map for each contributor to the pages they created
      HashMap<String, LinkedList<Integer>> contributorToPages = new HashMap<>();
      // split them up (contributorList)
      String [] contributors = contributorsList.split(",");
      // now for each contributor find there pages on this issue
      for(String contributor:contributors){
            findPagesOfContributor(contributor,contributorToPages);
      }
      // now put those pages in the list and return it
        for (String contributor: contributorToPages.keySet()){
            for(int pageNum: contributorToPages.get(contributor)){
                //putPageInList(contributor,pageNum,contributions);
            }
        }


      return contributions;

    }

    private void findPagesOfContributor(String contributor, HashMap<String, LinkedList<Integer>> contributorToPages) {

    }

    /*
        This method returns the useres of this weekly issue
        I use the issue object to store the users in because I dont know how to just
        return one string with a query for some reasone it has to be stored
        in an object
     */
    public String getContibutors(String issueTitle) throws Exception {
            String getUsers = "SELECT users FROM \"WeeklyComic\" WHERE issuetitle='"+issueTitle+"';";
            Issue issue = new Issue();
        jdbc.queryForObject(getUsers, new RowMapper<Issue>() {
            public Issue mapRow(ResultSet rs, int rowNum) throws SQLException {

                issue.setTitle(rs.getString("users"));
                return issue;
            }
        });
        return issue.getTitle();


    }
}
