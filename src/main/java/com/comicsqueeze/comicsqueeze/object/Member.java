package com.comicsqueeze.comicsqueeze.object;

import java.util.ArrayList;

public class Member {
    private String username;
    private String email;
    private String bio;
    private boolean admin;
    private String imgUrl;
    private Series currentSeries;
    private Issue currentIssue;
    private Page currentPage;
    private ArrayList<Series> seriesArrayList;
    private boolean createdWeekly;
    private boolean voted;


    public String getUsername() {return username;}
    public Boolean getAdminStatus() {return admin;}
    public String getEmail() {
        return email;
    }
    public String getBio() {
        return bio;
    }
    public String getImgUrl(){return imgUrl;}

    public void setUsername(String name){this.username = name;}
    public void setAdminStatus(boolean admin) {this.admin = admin;}
    public void setEmail(String email) {
        this.email = email;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setImgUrl(String imgUrl){this.imgUrl = imgUrl;}

    public Series getCurrentSeries() {
        return currentSeries;
    }

    public void setCurrentSeries(Series currentSeries) {
        this.currentSeries = currentSeries;
    }

    public Issue getCurrentIssue() {
        return currentIssue;
    }

    public void setCurrentIssue(Issue currentIssue) {
        this.currentIssue = currentIssue;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<Series> getSeriesArrayList() {
        return seriesArrayList;
    }

    public void setSeriesArrayList(ArrayList<Series> seriesArrayList) {
        this.seriesArrayList = seriesArrayList;
    }



    public boolean isCreatedWeekly() {
        return createdWeekly;
    }

    public void setCreatedWeekly(boolean createdWeekly) {
        this.createdWeekly = createdWeekly;
    }

    public void setVoted(boolean b) {
        this.voted = b;
    }
    public boolean getVoted(){
        return this.voted;
    }
}
