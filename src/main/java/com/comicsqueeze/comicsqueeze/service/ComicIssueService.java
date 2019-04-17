package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.repository.IssueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComicIssueService {
    @Autowired
    private IssueRepo issueRepo;
    public Issue findIssueByTitle(Member member,String seriesTitle, String issueTitle){
        return issueRepo.findByIssueTitle(member,seriesTitle,issueTitle);
    }
    public void createIssue(Issue newIssue){
        issueRepo.createIssue(newIssue);
    }
}
