package com.comicsqueeze.comicsqueeze.object;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Series {

    private boolean collaborative;
    private String creators;
    private String description;
    private double rating;
    private String title;
    private String tags;
    private LocalDateTime timestamp;
    private String username;
    private int views;
    private boolean weekly;
    private boolean flag;
    private ArrayList<Issue> issueArrayList;
    private int rateCounter;
    private String imgUrl;
    private String[] creatorArray;

    public String[] getCreatorArray() {
        return creatorArray;
    }

    public void setCreatorArray(String[] creatorArray) {
        this.creatorArray = creatorArray;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreators() {
        return creators;
    }

    public void setCreators(String creators) {
        this.creators = creators;
    }

    public String getTags() {
        return tags;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean isWeekly() {
        return weekly;
    }

    public void setWeekly(boolean weekly) {
        this.weekly = weekly;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ArrayList<Issue> getIssueArrayList() {
        return issueArrayList;
    }

    public void setIssueArrayList(ArrayList<Issue> issueArrayList) {
        this.issueArrayList = issueArrayList;
    }

    public int getRateCounter() {
        return rateCounter;
    }

    public void setRateCounter(int rateCounter) {
        this.rateCounter = rateCounter;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
