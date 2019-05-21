package com.comicsqueeze.comicsqueeze.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepo {

    @Autowired
    JdbcTemplate jdbc;

    public void storeNotification(String username, String seriesTitle, String body, String type){
        System.out.println("Got to notif repo");
        jdbc.update("INSERT INTO  \"Notifications\" (username, seriesTitle, reportBody, reportType)"
                + "VALUES(?,?,?,?,?)", username, seriesTitle, body, type, "link");
    }




}
