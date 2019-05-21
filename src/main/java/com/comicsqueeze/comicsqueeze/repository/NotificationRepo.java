package com.comicsqueeze.comicsqueeze.repository;


import com.comicsqueeze.comicsqueeze.object.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class NotificationRepo {

    @Autowired
    JdbcTemplate jdbc;

    public void storeNotification(String username, String body, String link, String type,String usernameto, Boolean read){
        System.out.println("Got to notif repo");
        jdbc.update("INSERT INTO  \"Notifications\" (username, body, link, type,usernameto,read)"
                + "VALUES(?,?,?,?,?,?)", username, body, link, type,usernameto,read);
    }

    public ArrayList<Notification> queryAllNotifications(String usernameTo){
        String findallNotifs ="SELECT * FROM \"Notifications\" WHERE usernameto='"+usernameTo+"' AND read='"+false+"';";
        try {


            List<Map<String, Object>> rows = jdbc.queryForList(findallNotifs);
            ArrayList<Notification> notifs = new ArrayList<>();

            for (Map rs : rows) {
                Notification notification = new Notification();
                notification.setUsername((String) rs.get("username"));
                notification.setBody((String) rs.get("body"));
                notification.setLink((String) rs.get("link"));
                notification.setRead((Boolean) rs.get("read"));
                notification.setUsernameto((String) rs.get("usernameto"));
                notification.setType((String) rs.get("type"));
                notifs.add(notification);
            }
            return notifs;
        }
        catch (Exception e){
            return null;
        }
    }




}
