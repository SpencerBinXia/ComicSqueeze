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

    public boolean insertSubscription(String subscriber, String seriesTitle, String seriesCreator, String imgUrl) {
        Subscription existing = subRepo.findSubscription(subscriber, seriesTitle, seriesCreator);
        if (existing == null) {
            System.out.println("null subscription reached");
            subRepo.insertSubscription(subscriber, seriesTitle, seriesCreator, imgUrl);
            subRepo.increaseFollows(seriesCreator);
            return true;
        }
        else
        {
            System.out.println("existing subscription reached");
            return false;
        }
    }

    public void removeSubscription(String subscriber, String seriesTitle, String seriesCreator) {
        Subscription existing = subRepo.findSubscription(subscriber, seriesTitle, seriesCreator);
        if (existing == null) {
            System.out.println("Remove null subscription");
            return;
        }
        else {
            System.out.println("Remove subscription");
            subRepo.deleteSubscription(subscriber, seriesTitle, seriesCreator);
            subRepo.decreaseFollows(seriesCreator);
            return;
        }
    }

    public void removeSubsFromSeries(String seriesTitle, String seriesCreator)
    {
        subRepo.deleteSubscriptionsFromSeries(seriesTitle, seriesCreator);
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

    public void increaseFollows(String seriesCreator){
        subRepo.increaseFollows(seriesCreator);
    }
    public void decreaseFollows(String seriesCreator){
        subRepo.decreaseFollows(seriesCreator);
    }

    public int sumSeriesSubscriptions(String seriesTitle, String seriesCreator)
    {
        int sum = subRepo.sumSeriesSubscriptions(seriesTitle, seriesCreator);
        return sum;
    }

    public void updateSubImage(String seriesTitle, String seriesCreator, String imgUrl)
    {
        subRepo.updateSubImgUrl(seriesTitle, seriesCreator, imgUrl);
    }

    public int sumUserSubscriptions(String username)
    {
        int sum = subRepo.sumUserSubscriptions(username);
        return sum;
    }

    public ArrayList<Subscription> queryAllSubscriptions (String subscriber){
        return subRepo.queryAllSubscriptions(subscriber);
    }
}
