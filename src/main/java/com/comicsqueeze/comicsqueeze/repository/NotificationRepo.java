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

    public boolean storeNotification(String username, String body, String link, String type,String usernameto, Boolean read, Boolean adminRead, String seriesName){
        if(notificationExist(username, link, usernameto, type)){
            return true;
        }

        System.out.println("Got to notif repo");
        jdbc.update("INSERT INTO  \"Notifications\" (username, body, link, type,usernameto,read,adminread,seriesname)"
                + "VALUES(?,?,?,?,?,?,?,?)", username, body, link, type,usernameto,read,adminRead,seriesName);
        return false;
    }

    public boolean notificationExist(String username, String link, String usernameto, String type){

        String query = "SELECT * FROM \"Notifications\" WHERE username = '" + username + "'AND link = '" + link + "'AND usernameto ='"
                + usernameto + "'AND type = '" + type + "';";
        List<Map<String, Object>> rows = jdbc.queryForList(query);
        if(rows.size() == 1){
            System.out.println("Notif already exists ");
            return true;
        }
        return false;
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
                notification.setAdminRead((Boolean) rs.get("adminread"));
                notification.setSeriesReported((String) rs.get("seriesname"));
                notifs.add(notification);
            }
            return notifs;
        }
        catch (Exception e){
            return null;
        }
    }


    public ArrayList<Notification> queryAdminNotifs() {
        String findallNotifs ="SELECT * FROM \"Notifications\" WHERE adminread='"+false+"';";
        try {

            ArrayList<Notification> notifs = new ArrayList<>();
            List<Map<String, Object>> rows = jdbc.queryForList(findallNotifs);

            for (Map rs : rows) {
                Notification notification = new Notification();
                notification.setUsername((String) rs.get("username"));
                notification.setBody((String) rs.get("body"));
                notification.setLink((String) rs.get("link"));
                notification.setRead((Boolean) rs.get("read"));
                notification.setUsernameto((String) rs.get("usernameto"));
                notification.setType((String) rs.get("type"));
                notification.setAdminRead((Boolean) rs.get("adminread"));
                notification.setSeriesReported((String) rs.get("seriesname"));
                notifs.add(notification);
            }
            return notifs;

        }
        catch (Exception e){
            return null;
        }
    }

    public void adminMarkRead(String userFrom, String link, String userTo) {
        String updateNotif = "UPDATE \"Notifications\" SET adminread='true' WHERE username='"+userFrom+"' AND link='"+link+"' AND usernameto='"+userTo+"';";
        jdbc.update(updateNotif);
    }

    public void userMarkRead(String userFrom, String link, String userTo) {
        String updateNotif = "UPDATE \"Notifications\" SET read='true' WHERE username='"+userFrom+"' AND link='"+link+"' AND usernameto='"+userTo+"';";
        jdbc.update(updateNotif);
    }
}
