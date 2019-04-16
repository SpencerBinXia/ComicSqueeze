package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Member;

import javax.servlet.http.HttpSession;
import com.comicsqueeze.comicsqueeze.repository.ProfileRepo;
import com.comicsqueeze.comicsqueeze.object.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class loginRegisterService {

    @Autowired
    private ProfileRepo userrepo;

    // someMember represents the registration info stored in a Member object
    public boolean registerMember(Member newMember) {
        System.out.println("register member reached");
        Member existing = userrepo.findByName(newMember.getUsername());
        if (existing == null) {
            System.out.println("null existing reached");
            userrepo.regMember(newMember);
            return true;
        }
        return false;
    }

    public boolean setBio(String name, String bio)
    {
        System.out.println("setbio");
        userrepo.setMemberBio(userrepo.findByName(name), bio);
        return true;
    }

    public Member findMember(String name){
        Member existing = userrepo.findByName(name);
        if (existing == null)
        {
            return null;
        }
        else
        {
            System.out.println("findMember " + existing.getUsername());
            return existing;
        }
    }
    public void setImgURl(Member member, String url){
        userrepo.setImgUrl(member,url);
    }
}
