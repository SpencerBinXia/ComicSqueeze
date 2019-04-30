package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.RateReview;

import javax.servlet.http.HttpSession;
import com.comicsqueeze.comicsqueeze.repository.ProfileRepo;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.repository.RateReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateReviewService {

    @Autowired
    RateReviewRepo rateRepo;

    public boolean createReview(RateReview newReview) {
        //Member existing = userrepo.findByName(newMember.getUsername());
        //if (existing == null) {
            System.out.println("null existing reached");
            rateRepo.createReview(newReview);
            return true;
        //}
        //return false;
    }
}
