package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.repository.IssueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ComicIssueService {
    @Autowired
    private IssueRepo issueRepo;
    public Issue findIssueByTitle(String username, String seriesTitle, String issueTitle){
        Issue existing = issueRepo.findByIssueTitle(username, seriesTitle, issueTitle);
        if (existing == null)
        {
            return null;
        }
        else
        {
            return existing;
        }
    }
    public void createIssue(Issue newIssue){
        issueRepo.createIssue(newIssue);
    }
    public ArrayList<Issue> queryAllIssuesFromASeries (Member member, Series series){
        return issueRepo.queryAllIssuesFromASeries(member, series);
    }
}
