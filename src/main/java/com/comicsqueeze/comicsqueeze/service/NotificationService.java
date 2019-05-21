package com.comicsqueeze.comicsqueeze.service;


import com.comicsqueeze.comicsqueeze.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepo;

    public void storeNotification(String username, String seriesTitle, String body, String type){
        System.out.println("Got to notif service");
        notificationRepo.storeNotification(username,seriesTitle,body,type);
    }
}

