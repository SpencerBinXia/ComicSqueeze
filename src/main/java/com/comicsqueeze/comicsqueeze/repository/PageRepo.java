package com.comicsqueeze.comicsqueeze.repository;


import com.comicsqueeze.comicsqueeze.object.Member;
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
import java.util.List;
import java.util.Map;

@Repository
public class PageRepo {
    @Autowired
    JdbcTemplate jdbc;

    public Page findByPageNumber(String username, int pageNumber, String seriesTitle, String issueTitle){
        String findPage = "SELECT * FROM \"Series\" WHERE username ='" + username + "' AND seriestitle='" + seriesTitle
                + "' AND issueTitle='" + issueTitle + "' AND pageNumber='" + pageNumber + "';";

        Page tempPage = new Page();
        try
        {
            jdbc.queryForObject(findPage, new RowMapper<Page>() {
                public Page mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempPage.setUsername(rs.getString("username"));
                    tempPage.setPublished(rs.getBoolean("published"));
                    tempPage.setImgurl(rs.getString("imgurl"));
                    tempPage.setVotes(rs.getInt("votes"));
                    tempPage.setSeries(rs.getString("series"));
                    tempPage.setIssue(rs.getString("issue"));
                    tempPage.setPagenumber(rs.getInt("pagenumber"));
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
        jdbc.update("INSERT INTO \"Page\"(username,series,issue,imgurl,pagenumber,votes,published)"
                        + "VALUES(?,?,?,?,?,?,?)", newPage.getUsername(),newPage.getSeries(),newPage.getIssue(),newPage.getImgurl(),
                newPage.getPagenumber(),newPage.getVotes(),newPage.isPublished());
    }

    public void deletePage(Page newPage){
        jdbc.update("DELETE FROM \"Page\" WHERE issue='" + newPage.getIssue() + "' AND series='" + newPage.getSeries() + "' AND username='"
                         + newPage.getUsername() + "' AND pagenumber='" + newPage.getPagenumber() + "';");
    }

    public void deletePages(String issue, String series, String username){
        jdbc.update("DELETE FROM \"Page\" WHERE issue='" + issue + "' AND series='" + series + "' AND username='" + username + "';");
    }

    public void deleteSeriesPages(String series, String username){
        jdbc.update("DELETE FROM \"Page\" WHERE series='" + series + "' AND username='" + username + "';");
    }

    public void setImgUrl(Page page, String username, String url){
        jdbc.update("UPDATE \"Page\" SET imgurl = '" + url+"' WHERE username = '" + username + "' AND pagenumber ='" + page.getPagenumber() + "';");
        System.out.println("Updated User's img in DB");

    }
    public ArrayList<Page> queryAllPages(Member member, String seriesTitle, String issueTitle) {
        String findPage = "SELECT * FROM \"Page\" WHERE username ='" + member.getUsername() + "' AND series='" + seriesTitle
                + "' AND issue='" + issueTitle +"';";
        List<Map<String, Object>> rows = jdbc.queryForList(findPage);
        ArrayList<Page> pages = new ArrayList<>();
        for (Map rs : rows) {
            Page tempPage = new Page();
            tempPage.setUsername((String)rs.get("username"));
            tempPage.setPublished((boolean)rs.get("published"));
            tempPage.setImgurl((String)rs.get("imgurl"));
            tempPage.setVotes((int)rs.get("votes"));
            tempPage.setSeries((String)rs.get("series"));
            tempPage.setIssue((String)rs.get("issue"));
            tempPage.setPagenumber((int)rs.get("pagenumber"));
            pages.add(tempPage);
        }
        return pages;
    }
}
