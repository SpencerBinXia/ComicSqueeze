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
import java.util.Enumeration;

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
        /*
            Create User in Firebase
         */
        Enumeration attrs =  session.getAttributeNames();
        while(attrs.hasMoreElements()) {
            session.removeAttribute((String)attrs.nextElement());
        }
        String message;
        try {
            UserRecord.CreateRequest request =  new UserRecord.CreateRequest()
                    .setEmail(Email)
                    .setEmailVerified(false)
                    .setPassword(Password)
                    .setDisplayName(Username);
            Member newMember = new Member();
            newMember.setUsername(Username);
            newMember.setEmail(Email);
            UserRecord userRecord = null;
            userRecord = FirebaseAuth.getInstance().createUser(request);
            /*
                Check if the username already exists
             */
            if(service.registerMember(newMember)){
//                m.addAttribute("userName",userRecord.getDisplayName());
//                session.setAttribute("username", Username);
                return "redirect:/";
            }
            /*
                if the username already exists cancel registration and delete user from firebase
             */
            else{
                FirebaseAuth.getInstance().deleteUser(userRecord.getUid());
                message= "Username already in use";
                m.addAttribute("err2",message);
                return "Register";
            }
        } catch (FirebaseAuthException e) {
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
