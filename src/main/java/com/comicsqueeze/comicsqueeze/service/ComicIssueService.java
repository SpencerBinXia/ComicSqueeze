package com.comicsqueeze.comicsqueeze.service;

import com.comicsqueeze.comicsqueeze.object.Issue;
import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.object.WeeklyComic;
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

    public void updateLastModified(String username, String seriesTitle, String issueTitle, Issue curIssue) {
        issueRepo.updateLastModified(username, seriesTitle, issueTitle, curIssue);
    }

    public void deleteIssue(String issueTitle, String series, String username) { issueRepo.deleteIssue(issueTitle, series, username); }

    public void deleteIssues(String series, String username) { issueRepo.deleteIssues(series, username); }


    public ArrayList<Issue> queryAllIssuesFromASeries (Member member, Series series){
        return issueRepo.queryAllIssuesFromASeries(member, series);
    }

    public void updatePageCount(String username, String seriesTitle, String issueTitle, int pageCount) {
    issueRepo.updatePageCount(username, seriesTitle, issueTitle, pageCount);
    }

    public String queryForWeeklyIssue() {
        return issueRepo.queryforWeeklyIssue();
    }
    public WeeklyComic queryForWeeklyComic(String thisWeekIssue) {
        return issueRepo.queryforWeeklyComic(thisWeekIssue);
    }


    public void addIssueCover(String username, String seriesTitle, String issueTitle, String imgurl) {
        issueRepo.addIssueCover(username,seriesTitle,issueTitle,imgurl);
    }

    public void editIssue(String username, String seriesTitle, String issueTitle, String desc) {
        issueRepo.editIssue(username, seriesTitle, issueTitle, desc);
    }

}
