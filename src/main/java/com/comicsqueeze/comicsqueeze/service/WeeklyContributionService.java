package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.repository.WeeklyContributionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WeeklyContributionService {
    @Autowired
    private WeeklyContributionRepo weeklyContributionRepo;
    public void updatePageCount(String issueTitle, int pageCount) {
        weeklyContributionRepo.updatePageCount(issueTitle,pageCount);

    }

    public void addContributor(String issueTitle, String username) {
        weeklyContributionRepo.addContributor(issueTitle,username);
    }
    public ArrayList<Page> queryAllContributions(String issue, int day){
        return weeklyContributionRepo.queryAllContributions(issue,day);
    }
    public void castVote(String issue, String contributor, int pageNum){
        weeklyContributionRepo.castVote(issue,contributor,pageNum);
    }
    public void setMemberCreatedWeekly(String username){
        weeklyContributionRepo.setMemberCreatedWeekly(username);
    }

}
