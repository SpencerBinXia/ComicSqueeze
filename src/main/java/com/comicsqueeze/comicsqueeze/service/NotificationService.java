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

    public void storeNotification(String username, String seriesTitle, String body, String type, String usernameto,Boolean read){
        System.out.println("Got to notif service");
        notificationRepo.storeNotification(username,seriesTitle,body,type,usernameto,read);
    }
    public ArrayList<Notification> queryAllNotifications(String usernameTo){
        return notificationRepo.queryAllNotifications(usernameTo);
    }
}
