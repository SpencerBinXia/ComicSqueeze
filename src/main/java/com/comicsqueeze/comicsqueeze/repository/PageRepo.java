package com.comicsqueeze.comicsqueeze.repository;


import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.object.Series;
//import com.sun.org.apache.xpath.internal.operations.Bool;
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
public class PageRepo {
    @Autowired
    JdbcTemplate jdbc;

    public Page findByPageNumber(String username, int pageNumber, String seriesTitle, String issueTitle){
        String findPage = "SELECT * FROM \"Series\" WHERE username = ? AND seriestitle= ? AND issueTitle= ? AND pageNumber= ?;";
        Page tempPage = new Page();
        try
        {
            jdbc.queryForObject(findPage, new Object[] {username, seriesTitle, issueTitle, pageNumber}, new RowMapper<Page>() {
                public Page mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempPage.setUsername(rs.getString("username"));
                    tempPage.setPublished(rs.getBoolean("published"));
                    tempPage.setImgurl(rs.getString("imgurl"));
                    tempPage.setVotes(rs.getInt("votes"));
                    tempPage.setSeries(rs.getString("series"));
                    tempPage.setIssue(rs.getString("issue"));
                    tempPage.setPagenumber(rs.getInt("pagenumber"));
                    tempPage.setCustom(rs.getBoolean("customupload"));
                    return tempPage;
                }
            });
        }
        catch (Exception e)
        {
            return null;
        }
        return tempPage;
    }

    public void createPage(Page newPage){
        System.out.println("THE IMGURL "+ newPage.getImgurl());
        jdbc.update("INSERT INTO \"Page\"(username,series,issue,imgurl,pagenumber,votes,published,customupload)"
                        + "VALUES(?,?,?,?,?,?,?,?)", newPage.getUsername(),newPage.getSeries(),newPage.getIssue(),newPage.getImgurl(),
                newPage.getPagenumber(),newPage.getVotes(),newPage.isPublished(),newPage.getCustom());
    }
    public void createWeeklyPage(Page newPage,int day){
        System.out.println("THE IMGURL "+ newPage.getImgurl());
        jdbc.update("INSERT INTO \"WeeklyPages\"(username,issue,imgurl,pagenumber,votes,published,dayOfWeek)"
                        + "VALUES(?,?,?,?,?,?,?)", newPage.getUsername(),newPage.getIssue(),newPage.getImgurl(),
                newPage.getPagenumber(),newPage.getVotes(),newPage.isPublished(),day);
    }

    public void deletePage(Page newPage){
        jdbc.update("DELETE FROM \"Page\" WHERE issue= ? AND series= ? AND username= ? AND pagenumber= ?;", newPage.getIssue(), newPage.getSeries(), newPage.getUsername(), newPage.getPagenumber());
    }

    public void deletePages(String issue, String series, String username){
        jdbc.update("DELETE FROM \"Page\" WHERE issue= ? AND series= ? AND username= ?;", issue, series, username);
    }

    public void deleteSeriesPages(String series, String username){
        jdbc.update("DELETE FROM \"Page\" WHERE series= ? AND username= ?;", series, username);
    }

    public void setImgUrl(Page page, String username, String url){
        jdbc.update("UPDATE \"Page\" SET imgurl = ? WHERE username = ? AND pagenumber = ?;", url, username, page.getPagenumber());
        System.out.println("Updated User's img in DB");

    }

    public void updatePages(ArrayList<Page> issuePages)
    {
        for (int i = 0;i <issuePages.size();i++)
        {
            System.out.println("update repo" + issuePages.get(i).isPublished() + issuePages.get(i).getPagenumber());
            String updatePage = "UPDATE \"Page\" SET published= ? WHERE username = ? AND series= ? AND issue= ? AND pagenumber= ?;";
            jdbc.update(updatePage, issuePages.get(i).isPublished(), issuePages.get(i).getUsername(), issuePages.get(i).getSeries(), issuePages.get(i).getIssue(), issuePages.get(i).getPagenumber());
        }
    }
    public ArrayList<Page> queryAllPages(Member member, String seriesTitle, String issueTitle) {
        String findPage = "SELECT * FROM \"Page\" WHERE username = ? AND series= ? AND issue= ?;";
        List<Map<String, Object>> rows = jdbc.queryForList(findPage, member.getUsername(), seriesTitle, issueTitle);
        ArrayList<Page> pages = new ArrayList<>();
        int i = 1;
        int p = 1;
        for (Map rs : rows) {
            Page tempPage = new Page();
            tempPage.setUsername((String)rs.get("username"));
            tempPage.setPublished((boolean)rs.get("published"));
            tempPage.setImgurl((String)rs.get("imgurl"));
            tempPage.setVotes((int)rs.get("votes"));
            tempPage.setSeries((String)rs.get("series"));
            tempPage.setIssue((String)rs.get("issue"));
            tempPage.setPagenumber((int)rs.get("pagenumber"));
            tempPage.setCustom((Boolean) rs.get("customupload"));
            pages.add(tempPage);
        }
        return pages;
    }

}
