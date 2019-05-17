package com.comicsqueeze.comicsqueeze.repository;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class SearchRepo {
    @Autowired
    JdbcTemplate jdbc;
    RateReviewRepo rateReviewRepo;

    public ArrayList<Series> searchAllSeriesByTitle(String searchString) {
        System.out.println("Matching series titles with " + searchString);
        searchString = searchString.concat(":*");
        //String findSeries = "SELECT * FROM \"Series\" WHERE seriestitle ='" + searchString + "';";
        String findSeries = "SELECT * FROM \"Series\", to_tsquery('" + searchString + "') as q WHERE(tsv_series_title @@ q);";
        return findMatchedSeries(findSeries);
        //Handle not found results for series title and username
    }

    public ArrayList<Series> searchForMatchingTags(String searchString){
        System.out.println("Matching Tags with" + searchString);
        searchString = searchString.concat(":*");
        String findSeries = "SELECT * FROM \"Series\", to_tsquery('" + searchString + "') as q WHERE(tsv_tags @@ q);";
        //String findTagsFromSeries = "SELECT * FROM \"Series\" WHERE find_in_set('" + searchString + "',tags)" + ";";
        //String findTagsFromSeries = "SELECT * FROM \"Series\" WHERE'" + searchString + "'= ANY (string_to_array(tags,','))" + ";";
        //where '8' = ANY (string_to_array(some_column,','))
        return findMatchedSeries(findSeries);
    }

    public ArrayList<Series> sortByRecent(){
        String findSortByRecent = "SELECT timestamp,seriestitle,username,tags FROM \"Series\" ORDER BY timestamp;";
        ArrayList<Series> sortedSeries = new ArrayList<>();
        List<Map<String,Object>> rows = jdbc.queryForList(findSortByRecent);
        for(Map rs : rows){

        }
         try{

        }
        catch(Exception e){

        }
        return sortedSeries;
    }
    public ArrayList<Member> searchForUsernames(String searchString){
        System.out.println("Matching users with" + searchString);
        //String findUser = "SELECT * FROM \"Member\" WHERE username ='" + searchString + "';";
        searchString = searchString.concat(":*");
        String findUsers = "SELECT * FROM \"Member\", to_tsquery('" + searchString + "') as q WHERE (tsv_username @@ q);";
        ArrayList<Member> members = new ArrayList();
        try
        {
            /*
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
            */
            List<Map<String,Object>> rows = jdbc.queryForList(findUsers);
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

    public ArrayList<Series> findMatchedSeries(String findSeries){
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
            Date tempDate = ((Date)rs.get("timestamp"));
            tempSeries.setTimestamp(LocalDateTime.ofInstant(tempDate.toInstant(), ZoneId.systemDefault()));
            System.out.println("The timestamp : " + tempSeries.getTimestamp());
            tempSeries.setRateCounter((int)rs.get("ratecounter"));
            series.add(tempSeries);
        }
        return series;
    }

    public ArrayList<String> getAllTags(){
        String query = "SELECT tags FROM \"Series\" AS document;";
        ArrayList<String> allTags = new ArrayList<>();
        try{
            List<Map<String,Object>> rows = jdbc.queryForList(query);
            for(Map rs : rows){
                allTags.add((String) rs.get("tags"));
            }
        }
        catch (Exception e){
            return null;
        }
        return allTags;
    }
    public ArrayList<String> getAllMembers(){
        String query = "SELECT username FROM \"Member\" AS document;";
        ArrayList<String> allMembers = new ArrayList<>();
        try{
            List<Map<String,Object>> rows = jdbc.queryForList(query);
            for(Map rs : rows){
                allMembers.add((String) rs.get("username") + ", ");
            }
        }
        catch(Exception e){
            return null;
        }
        return allMembers;
    }
    public ArrayList<String> deepSearch(String searchString){
        String query = "SELECT to_tsvector(seriestitle) FROM \"Series\" AS document;";
        String stringlist = "yup";
        ArrayList<String> alldata = new ArrayList<>();
        try {
            List<Map<String, Object>> rows = jdbc.queryForList(query);
            System.out.println(rows.toString());
            for(Map rs : rows){
                alldata.add((String)rs.get("seriestitle"));
            }
        }
        catch(Exception e){
            return null;
        }
        ArrayList<String> allMembers = getAllMembers();
        ArrayList<String> allTags = getAllTags();
        for(int i =0; i < allMembers.size(); i++){
            alldata.add(allMembers.get(i));
        }
        for(int i =0; i < allTags.size(); i++){
            alldata.add(allTags.get(i));
        }
        return alldata;
    }
/*
    public ArrayList<Series> sortByHighLow(ArrayList<Member> members){
        String findSortHighLow = "SELECT * FROM \"ratereview\" ;";
        ArrayList<Series> sortedSeries = new ArrayList<>();


        return sortedSeries;
    }
*/
}
