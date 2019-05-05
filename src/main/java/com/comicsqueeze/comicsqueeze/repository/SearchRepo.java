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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchRepo {
    @Autowired
    JdbcTemplate jdbc;
    RateReviewRepo rateReviewRepo;

    public ArrayList<Series> searchAllSeriesByTitle(String searchString) {
        System.out.println("Matching series titles with " + searchString);
        String findSeries = "SELECT * FROM \"Series\" WHERE seriestitle ='" + searchString + "';";
        return findMatchedSeries(findSeries);
        //Handle not found results for series title and username
    }

    public ArrayList<Series> searchForMatchingTags(String searchString){
        System.out.println("Matching Tags with" + searchString);
        //String findTagsFromSeries = "SELECT * FROM \"Series\" WHERE find_in_set('" + searchString + "',tags)" + ";";
        String findTagsFromSeries = "SELECT * FROM \"Series\" WHERE'" + searchString + "'= ANY (string_to_array(tags,','))" + ";";
        //where '8' = ANY (string_to_array(some_column,','))
        return findMatchedSeries(findTagsFromSeries);
    }

    public ArrayList<Series> sortByRecent(){
        String findSortByRecent = "SELECT timestamp,seriestitle,username FROM \"Series\" ORDER BY timestamp;";
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
    public Member searchForUsername(String searchString){
        System.out.println("Matching users with" + searchString);
        String findUser = "SELECT * FROM \"Member\" WHERE username ='" + searchString + "';";
        Member tempMember = new Member();
        try
        {
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
        }
        catch (Exception e)
        {
            return null;
        }
        System.out.println("Value in searchForUsername in SearchRepo " + tempMember.getUsername());
        return tempMember;
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
            tempSeries.setTimestamp((LocalDateTime)(rs.get("time_stamp")));
            System.out.println("The timestamp : " + tempSeries.getTimestamp());
            tempSeries.setRateCounter((int)rs.get("ratecounter"));
            series.add(tempSeries);
        }
        return series;
    }
    public ArrayList<String> deepSearch(String searchString){
        String query = "SELECT seriestitle FROM \"Series\" AS document;";
        String stringlist = "yup";
        ArrayList<String> alldata = new ArrayList<>();
        try {
            List<Map<String, Object>> rows = jdbc.queryForList(query);
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
/*
    public ArrayList<Series> sortByHighLow(ArrayList<Member> members){
        String findSortHighLow = "SELECT * FROM \"ratereview\" ;";
        ArrayList<Series> sortedSeries = new ArrayList<>();


        return sortedSeries;
    }
*/
}
