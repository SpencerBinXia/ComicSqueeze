package com.comicsqueeze.comicsqueeze.object;
import java.util.ArrayList;

public class Series {

    private boolean collaborative;
    private ArrayList<String> creators = new ArrayList<String>();
    private String description;
    private double rating;
    private String title;
    private ArrayList<String> tags = new ArrayList<String>();
    private long timestamp;
    private String username;
    private int views;
    private boolean weekly;
    private boolean flag;

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public ArrayList<String> getCreators() {
        return creators;
    }

    public void setCreators(ArrayList<String> creators) {
        this.creators = creators;
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
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
}
