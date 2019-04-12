package com.comicsqueeze.comicsqueeze.controller;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.service.loginRegisterService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

    @Autowired
    private loginRegisterService service;

    @RequestMapping("/register")
    public String home(Model model) {
        return "Register";
    }

    

    @RequestMapping("/registerUser")
    public String registerUser(Model m,@RequestParam String Username, @RequestParam String Password, @RequestParam String Email, @RequestParam String First, @RequestParam String Last, HttpSession session) {

        Member newMember = new Member();
        newMember.setUsername(Username);
        newMember.setEmail(Email);
        service.registerMember(newMember);
        try {
            UserRecord.CreateRequest request =  new UserRecord.CreateRequest()
                    .setEmail(Email)
                    .setEmailVerified(false)
                    .setPassword(Password)
                    .setDisplayName(Username);

            UserRecord userRecord = null;
            userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            m.addAttribute("userName",userRecord.getDisplayName());
            session.setAttribute("username", Username);
            return "CurrentUserProfile";
        } catch (FirebaseAuthException e) {
            String message;
            if (e.getMessage().equals("User management service responded with an error")){
                message= "Email already in use";
            }
            else{
                message = e.getMessage();
            }
            m.addAttribute("err2",message);

            m.addAttribute(("err3"),"registration unsuccessful make sure all fields are valid");
            return "Register";
        } catch (IllegalArgumentException e) {
//            String message;
//            if (e.getMessage().equals("User management service responded with an error")){
//                message= "Email already in use";
//            }
//            else{
//                message = e.getMessage();
//            }
//            m.addAttribute("err2",message);
            m.addAttribute(("err3"),"registration unsuccessful make sure all fields are valid");
            return "Register";
        }

    }
}
