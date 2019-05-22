package com.comicsqueeze.comicsqueeze.service;


import com.comicsqueeze.comicsqueeze.object.Notification;
import com.comicsqueeze.comicsqueeze.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepo;

    public void storeNotification(String username,String body,String link, String type, String usernameto,Boolean read, Boolean adminRead, String seriesName){
        System.out.println("Got to notif service");
        notificationRepo.storeNotification(username,body,link,type,usernameto,read,adminRead,seriesName);
    }
    public ArrayList<Notification> queryAllNotifications(String usernameTo){
        return notificationRepo.queryAllNotifications(usernameTo);
    }

    public ArrayList<Notification> queryAdminNotifs() {
       return notificationRepo.queryAdminNotifs();
    }
}

