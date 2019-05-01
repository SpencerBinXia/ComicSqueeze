package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.repository.WeeklyContributionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
