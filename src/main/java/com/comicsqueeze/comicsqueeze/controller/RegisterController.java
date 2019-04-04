package com.comicsqueeze.comicsqueeze.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class RegisterController {

    @RequestMapping("/register")
    public String home(Model model) {
        return "Register";
    }

    

    @RequestMapping("/registerUser")
    public String registerUser(Model m,@RequestParam String Username, @RequestParam String Password, @RequestParam String Email, @RequestParam String First, @RequestParam String Last) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(Email)
                    .setEmailVerified(false)
                    .setPassword(Password)
                    .setDisplayName(Username);

            UserRecord userRecord = null;
            userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
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
        }

    }
}
