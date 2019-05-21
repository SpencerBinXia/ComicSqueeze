package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.RateReview;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.comicsqueeze.comicsqueeze.service.loginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class findInvitedUserController {

    @Autowired
    private loginRegisterService service;

    @RequestMapping(value = "/findInvitedUser", method = RequestMethod.POST)
    public String controllerMethod(@RequestParam(value = "invitedUser") String invitedUser, Model model, HttpSession session) {

        Member invitedMember = service.findMember(invitedUser);
        if (invitedMember != null) {
            return "OK";
        } else {
            return "null";
        }
    }
}
