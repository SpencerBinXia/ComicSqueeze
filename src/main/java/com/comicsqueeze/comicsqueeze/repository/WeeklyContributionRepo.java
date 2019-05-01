package com.comicsqueeze.comicsqueeze.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WeeklyContributionRepo {
    @Autowired
    JdbcTemplate jdbc;

    public void updatePageCount(String issueTitle, int pageCount) {
        String updateWeekly= "UPDATE \"WeeklyComic\" SET PAGES= '"+ pageCount+"';";
        jdbc.update(updateWeekly);
    }

    public void addContributor(String username) {
        String updateWeekly= "UPDATE \"WeeklyComic\" SET USERS= '"+ username+",';";
        jdbc.update(updateWeekly);
    }
}
