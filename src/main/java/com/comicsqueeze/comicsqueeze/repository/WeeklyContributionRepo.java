package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
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
    @Autowired PageRepo pageRepo;
    @Autowired
    IssueRepo issueRepo;

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
           return null;
        }
        if (contributorsList==null){
            return  null;
        }
        //map for each contributor to the pages they created
      HashMap<String, LinkedList<Integer>> contributorToPages = new HashMap<>();
      // split them up (contributorList)
      String [] contributors = contributorsList.split(",");
      // now for each contributor find there pages on this issue
      for(String contributor:contributors){
            findPagesOfContributor(contributor,contributorToPages,day,issueTitle);
      }
      // now put those pages in the list and return it
        for (String contributor: contributorToPages.keySet()){
            for(int pageNum: contributorToPages.get(contributor)){
                putPageInList(contributor,pageNum,contributions,issueTitle);
            }
        }


      return contributions;

    }

    private ArrayList<Page> putPageInList(String contributor, int pageNum, ArrayList<Page> contributions,String thisWeekIssue) {
        String findPage = "SELECT * FROM \"WeeklyPages\" WHERE username ='" + contributor + "' AND pagenumber='" + pageNum +"' AND issue='"+thisWeekIssue+"';";
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

    public HashMap<String, LinkedList<Integer>> findPagesOfContributor(String contributor, HashMap<String, LinkedList<Integer>> contributorToPages,int day, String thisWeekIssue) {
        String getPages = "SELECT * FROM \"WeeklyPages\" WHERE username='"+contributor+"' AND dayofweek='"+day+"' AND issue ='"+thisWeekIssue+"';";
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
        try {
            jdbc.queryForObject(getUsers, new RowMapper<Issue>() {
                public Issue mapRow(ResultSet rs, int rowNum) throws SQLException {

                    issue.setTitle(rs.getString("users"));
                    return issue;
                }
            });
        }
        catch (Exception e){
            return null;
        }
        return issue.getTitle();


    }
    public void castVote(String issue, String contributor, int pageNum){
        String curVotes = "SELECT votes FROM \"WeeklyPages\" WHERE issue='"+issue+"' AND username='"+contributor+"' AND pagenumber='"+pageNum+"';";
        Page page = new Page();
        jdbc.queryForObject(curVotes, new RowMapper<Page>() {
            public Page mapRow(ResultSet rs, int rowNum) throws SQLException {
                page.setVotes(rs.getInt("votes"));
                return page;
            }
        });
        Integer newVotes = page.getVotes()+1;
        String updatePageVotes = "UPDATE \"WeeklyPages\" SET VOTES= '" +newVotes + "' WHERE issue='"+issue+"' AND username='"+contributor+"' AND pagenumber='"+pageNum+"';";
        jdbc.update(updatePageVotes);
    }
    public void setMemberCreatedWeekly(String username){
        String updatemember ="UPDATE \"Member\" SET CREATEDWEEKLY='"+true+"' WHERE username='" + username + "';";
        jdbc.update(updatemember);
    }


    public String checkIfCreatedPage(String username, String thisWeekIssue, int dayOfWeek) {
        String isCreated = "SELECT * FROM \"WeeklyPages\" WHERE username='"+username+"' AND dayofweek='"+dayOfWeek+"' AND issue='"+thisWeekIssue+"';";
        Member member = new Member();
        try {
            jdbc.queryForObject(isCreated, new RowMapper<Member>() {
                public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                    member.setUsername(rs.getString("username"));
                    return member;
                }
            });
        }
        catch (Exception e){
            return  null;
        }
        if (member.getUsername()!=null){
            return "created";
        }
        return null;
    }

    public Page calculateBestPage(String thisWeekIssue) {
        ArrayList<Page> pages = new ArrayList<>();
        String findPage = "SELECT * FROM \"WeeklyPages\" WHERE issue ='" + thisWeekIssue +"';";
        List<Map<String, Object>> rows = jdbc.queryForList(findPage);
        int i = 0;
        try {
            for (Map rs : rows) {
                Page tempPage = new Page();
                tempPage.setUsername((String) rs.get("username"));
                tempPage.setPublished((boolean) rs.get("published"));
                tempPage.setImgurl((String) rs.get("imgurl"));
                tempPage.setVotes((int) rs.get("votes"));
                tempPage.setSeries("WeeklyComic");
                tempPage.setIssue((String) rs.get("issue"));
                tempPage.setPagenumber((int) rs.get("pagenumber"));
                tempPage.setDayOfWeekCreated((int) rs.get("dayofweek"));
                tempPage.setPageArrayNumber(i);
                pages.add(tempPage);
                i++;
            }
        }
        catch (Exception e){
            return  null;
        }
        Page maxVotes = new Page();
        maxVotes.setUsername("max.votes");
        maxVotes.setVotes(0);
        for (Page page :pages){
            if(page.getVotes()>maxVotes.getVotes()){
                maxVotes = page;
            }
        }
        // if no page had votes greater than 0 aka no max then select at random
        if(maxVotes.getUsername().equals("max.votes")){
            Random r = new Random();
            int index = r.nextInt()+pages.size();
            return pages.get(index);
        }
        return maxVotes;
    }

    public void addMaxVotesToSeries(Page maxVotes) {
       String updatePagePublished = "UPDATE \"WeeklyPages\" SET PUBLISHED= 'true' WHERE issue='"+maxVotes.getIssue()+"' AND username='"+maxVotes.getUsername()+"' AND dayofweek='"+maxVotes.getDayOfWeekCreated()+"';";
        jdbc.update(updatePagePublished);

    }

    public ArrayList<Issue> getWeeklyIssues() {
        return issueRepo.queryforWeeklyIssues();
    }

    public Issue queryForIssue(String issueTitle) {
        return issueRepo.queryforWeeklyIssue(issueTitle);
    }

    public ArrayList<Page> queryAllIssuePages(String issueTitle) {
        ArrayList<Page> pages = new ArrayList<>();
        String findPage = "SELECT * FROM \"WeeklyPages\" WHERE issue ='" + issueTitle +"' AND published='true';";
        List<Map<String, Object>> rows = jdbc.queryForList(findPage);
        int i = 0;
        try {
            for (Map rs : rows) {
                Page tempPage = new Page();
                tempPage.setUsername((String) rs.get("username"));
                tempPage.setPublished((boolean) rs.get("published"));
                tempPage.setImgurl((String) rs.get("imgurl"));
                tempPage.setVotes((int) rs.get("votes"));
                tempPage.setSeries("WeeklyComic");
                tempPage.setIssue((String) rs.get("issue"));
                tempPage.setPagenumber((int) rs.get("pagenumber"));
                tempPage.setDayOfWeekCreated((int) rs.get("dayofweek"));
                tempPage.setPageArrayNumber(i);
                pages.add(tempPage);
                i++;
            }
        }
        catch (Exception e){
            return  null;
        }
        return pages;
    }
}
