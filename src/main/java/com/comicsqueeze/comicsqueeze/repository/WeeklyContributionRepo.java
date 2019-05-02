package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    public ArrayList<Page> queryAllContributions(String issueTitle,int day)  {
      ArrayList<Page> contributions = new ArrayList<>();
      // get all the contributors for this issue
        String contributorsList = null;
        try {
            contributorsList = getContibutors(issueTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //map for each contributor to the pages they created
      HashMap<String, LinkedList<Integer>> contributorToPages = new HashMap<>();
      // split them up (contributorList)
      String [] contributors = contributorsList.split(",");
      // now for each contributor find there pages on this issue
      for(String contributor:contributors){
            findPagesOfContributor(contributor,contributorToPages,day);
      }
      // now put those pages in the list and return it
        for (String contributor: contributorToPages.keySet()){
            for(int pageNum: contributorToPages.get(contributor)){
                putPageInList(contributor,pageNum,contributions);
            }
        }


      return contributions;

    }

    private ArrayList<Page> putPageInList(String contributor, int pageNum, ArrayList<Page> contributions) {
        String findPage = "SELECT * FROM \"WeeklyPages\" WHERE username ='" + contributor + "' AND pagenumber='" + pageNum +"';";
        List<Map<String, Object>> rows = jdbc.queryForList(findPage);
       int i = 0;
        for (Map rs : rows) {
            Page tempPage = new Page();
            tempPage.setUsername((String)rs.get("username"));
            tempPage.setPublished((boolean)rs.get("published"));
            tempPage.setImgurl((String)rs.get("imgurl"));
            tempPage.setVotes((int)rs.get("votes"));
            tempPage.setSeries("WeeklyComic");
            tempPage.setIssue((String)rs.get("issue"));
            tempPage.setPagenumber((int)rs.get("pagenumber"));
            tempPage.setPageArrayNumber(i);
            contributions.add(tempPage);
            i++;
        }
        return contributions;
    }

    public HashMap<String, LinkedList<Integer>> findPagesOfContributor(String contributor, HashMap<String, LinkedList<Integer>> contributorToPages,int day) {
        String getPages = "SELECT * FROM \"WeeklyPages\" WHERE username='"+contributor+"' AND dayofweek='"+day+"';";
        List<Map<String, Object>> rows = jdbc.queryForList(getPages);
        for (Map rs : rows) {
            if(contributorToPages.get(contributor)==null){
                contributorToPages.put(contributor, new LinkedList<Integer>());
                contributorToPages.get(contributor).add((int) (rs.get("pagenumber")));
            }
            else {
                contributorToPages.get(contributor).add((int) (rs.get("pagenumber")));
            }
        }



        return contributorToPages;
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
    public void castVote(String issue, String contributor, int pageNum){

        String updatePageVotes = "UPDATE \"WeeklyPages\" SET VOTES= '" +1 + "' WHERE issue='"+issue+"' AND username='"+contributor+"' AND pagenumber='"+pageNum+"';";
        jdbc.update(updatePageVotes);
    }
    public void setMemberCreatedWeekly(String username){
        String updatemember ="UPDATE \"Member\" SET CREATEDWEEKLY='"+true+"' WHERE username='" + username + "';";
        jdbc.update(updatemember);
    }


}
