package com.comicsqueeze.comicsqueeze.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepo {

    @Autowired
    JdbcTemplate jdbc;




}
