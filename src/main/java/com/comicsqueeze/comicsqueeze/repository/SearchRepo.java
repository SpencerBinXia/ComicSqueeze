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
    @Autowired
    RateReviewRepo rateReviewRepo;
    Double avgRating;

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
        System.out.println("got here 4");
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
                tempMember.setBio((String)rs.get("bio"));
                tempMember.setAdminStatus((boolean)rs.get("admin"));
                tempMember.setEmail((String)rs.get("email"));
                tempMember.setImgUrl((String)rs.get("imgurl"));
                tempMember.setFollows((Integer) rs.get("follows"));
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
        try{
            for (Map rs : rows) {
                Series tempSeries = new Series();
                tempSeries.setTitle((String)rs.get("seriestitle"));
                tempSeries.setDescription((String)rs.get("description"));
                tempSeries.setUsername((String)rs.get("username"));
                tempSeries.setWeekly((boolean)rs.get("weekly"));
                tempSeries.setTags((String)rs.get("tags"));
                tempSeries.setCreators((String)rs.get("creators"));
                Date tempDate = ((Date)rs.get("timestamp"));
                tempSeries.setTimestamp(LocalDateTime.ofInstant(tempDate.toInstant(), ZoneId.systemDefault()));
                //System.out.println("The timestamp : " + tempSeries.getTimestamp());
                tempSeries.setRateCounter((int)rs.get("ratecounter"));
                tempSeries.setImgUrl((String)rs.get("imgurl"));
                try {
                    avgRating = rateReviewRepo.queryAverageReview(tempSeries.getTitle(), tempSeries.getUsername());
                    tempSeries.setRating(avgRating);
                } catch (Exception e) {
                    tempSeries.setRating(-1.0);
                }
                series.add(tempSeries);
            }
        }
        catch (Exception e){
            return null;
        }
        return series;
    }



//  GETS ALL SERIES ON EMPTY SEARCH
//    public ArrayList<Series> getTopSeries(){
//        String seriesQuery = "SELECT * FROM \"Series\";";
//        List<Map<String, Object>> rows = jdbc.queryForList(seriesQuery);
//        ArrayList<Series> series = new ArrayList<>();
//        for (Map rs : rows) {
//            Series tempSeries = new Series();
//            tempSeries.setTitle((String)rs.get("seriestitle"));
//            tempSeries.setDescription((String)rs.get("description"));
//            tempSeries.setUsername((String)rs.get("username"));
//            tempSeries.setWeekly((boolean)rs.get("weekly"));
//            tempSeries.setTags((String)rs.get("tags"));
//            tempSeries.setCreators((String)rs.get("creators"));
//            Date tempDate = ((Date)rs.get("timestamp"));
//            tempSeries.setTimestamp(LocalDateTime.ofInstant(tempDate.toInstant(), ZoneId.systemDefault()));
//            System.out.println("The timestamp : " + tempSeries.getTimestamp());
//            tempSeries.setImgUrl((String)rs.get("imgurl"));
//            try {
//                avgRating = rateReviewRepo.queryAverageReview(tempSeries.getTitle(), tempSeries.getUsername());
//                tempSeries.setRating(avgRating);
//            } catch (Exception e) {
//                tempSeries.setRating(-1.0);
//            }
//            series.add(tempSeries);
//        }
//        return series;
//    }
    public ArrayList<Series> getTopSeries(){
        String query = "SELECT avg(rr.rating), seriestitle, username, description, timestamp, tags,creators,weekly,imgurl FROM \"ratereview\" rr " +
                "INNER JOIN \"Series\" USING(seriestitle) WHERE \"Series\".username = rr.seriescreator " +
                "AND \"Series\".seriestitle = rr.seriestitle GROUP BY seriestitle, username ORDER BY avg(rr.rating) DESC;";
        List<Map<String, Object>> rows = jdbc.queryForList(query);
        ArrayList<Series> series = new ArrayList<>();
        for (Map rs : rows) {
            Series tempSeries = new Series();
            tempSeries.setTitle((String)rs.get("seriestitle"));
            tempSeries.setDescription((String)rs.get("description"));
            tempSeries.setUsername((String)rs.get("username"));
            tempSeries.setRating((double)rs.get("avg"));
            tempSeries.setWeekly((boolean)rs.get("weekly"));
            tempSeries.setTags((String)rs.get("tags"));
            tempSeries.setCreators((String)rs.get("creators"));
            Date tempDate = ((Date)rs.get("timestamp"));
            tempSeries.setTimestamp(LocalDateTime.ofInstant(tempDate.toInstant(), ZoneId.systemDefault()));
            System.out.println("The timestamp : " + tempSeries.getTimestamp());
            tempSeries.setImgUrl((String)rs.get("imgurl"));
            series.add(tempSeries);
        }
        return series;
    }
    //Returns first 20 results
    public ArrayList<Series> getAllSeries(){
        String query = "SELECT * FROM \"Series\" fetch first 20 rows only;";
        return findMatchedSeries(query);
    }
    public ArrayList<Member> getAllMembers(){
        String query = "SELECT * FROM \"Series\" fetch first 20 rows only;";
        ArrayList<Member> members = new ArrayList<>();
        try{
            List<Map<String,Object>> rows = jdbc.queryForList(query);
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

    public ArrayList<String> getAllTagsstring(){
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
    public ArrayList<String> getAllMembersstring(){
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
        ArrayList<String> allMembers = getAllMembersstring();
        ArrayList<String> allTags = getAllTagsstring();
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
