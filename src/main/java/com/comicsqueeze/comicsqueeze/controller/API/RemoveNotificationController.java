package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Notification;
import com.comicsqueeze.comicsqueeze.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
public class RemoveNotificationController {
    @Autowired
    NotificationService notificationService;
    @RequestMapping("/removeNotification")
    public String removeNotification(@RequestParam("usernameFrom") String userFrom, @RequestParam("link") String link, @RequestParam("usernameTo") String userTo, HttpSession session){
        Member member = (Member) session.getAttribute("curMember");
        if (member.getAdminStatus()) {
            notificationService.adimnMarkRead(userFrom, link, userTo);
            if (userTo.equals(member.getUsername())){
                notificationService.userMarkRead(userFrom, link, userTo);
            }
        }
        else{
            notificationService.userMarkRead(userFrom, link, userTo);
        }
        return "redirect:/yourprofile";

    }
}
