package com.comicsqueeze.comicsqueeze.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepo {

    @Autowired
    JdbcTemplate jdbc;

    public void storeNotification(String username, String body, String link, String type){
        System.out.println("Got to notif repo");
        jdbc.update("INSERT INTO  \"Notifications\" (username, body, link, type)"
                + "VALUES(?,?,?,?)", username, body, link, type);
    }




}
