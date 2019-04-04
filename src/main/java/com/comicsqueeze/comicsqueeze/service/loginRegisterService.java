package com.comicsqueeze.comicsqueeze.service;

import javax.servlet.http.HttpSession;
import com.comicsqueeze.comicsqueeze.repository.ProfileRepo;
import com.comicsqueeze.comicsqueeze.object.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class loginRegisterService {

    @Autowired
    private ProfileRepo userrepo;

    // someUser represents the registration info stored in a User object
    public boolean registerUser(User someUser, HttpSession session) {
        User existing = userrepo.findByName(someUser.getUsername());
        if (existing == null) {
            User newUser = new User();
            newUser.setUsername(someUser.getUsername());
            userrepo.regUser(newUser);
            return true;
        }
        return false;
    }
}
