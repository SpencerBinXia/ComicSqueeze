package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.RateReview;

import javax.servlet.http.HttpSession;

import com.comicsqueeze.comicsqueeze.object.Subscription;
import com.comicsqueeze.comicsqueeze.repository.ProfileRepo;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.repository.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepo subRepo;

    public boolean insertSubscription(String subscriber, String seriesTitle, String seriesCreator) {
        Subscription existing = subRepo.findSubscription(subscriber, seriesTitle, seriesCreator);
        if (existing == null) {
            System.out.println("null review reached");
            subRepo.insertSubscription(subscriber, seriesTitle, seriesCreator);
            return true;
        }
        else
        {
            System.out.println("existing review reached");
            return false;
        }
    }

    public Subscription findSubscription(String subscriber, String seriesTitle, String seriesCreator){
        Subscription existing = subRepo.findSubscription(subscriber, seriesTitle, seriesCreator);
        if (existing == null)
        {
            return null;
        }
        else
        {
            return existing;
        }
    }

    public void removeSubscription(String subscriber, String seriesTitle, String seriesCreator)
    {
        subRepo.deleteSubscription(subscriber, seriesTitle, seriesCreator);
    }

    public int sumSeriesSubscriptions(String seriesTitle, String seriesCreator)
    {
        int sum = subRepo.sumSeriesSubscriptions(seriesTitle, seriesCreator);
        return sum;
    }

    public ArrayList<Subscription> queryAllSubscriptions (String subscriber){
        return subRepo.queryAllSubscriptions(subscriber);
    }
}
