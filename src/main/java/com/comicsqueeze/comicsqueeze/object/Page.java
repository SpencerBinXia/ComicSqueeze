package com.comicsqueeze.comicsqueeze.object;

import java.util.Comparator;

public class Page implements Comparable<Page> {

    private String issue;
    private String imgurl;
    private String creator;
    private int votes;
    private int pagenumber;
    private boolean published;
    private String username;
    private String series;
    private int pageArrayNumber;
    private int publishedArrayNumber;
    private int dayOfWeekCreated;
    private Boolean custom;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int compareTo(Page page)
    {
        if (this.getPagenumber() > page.getPagenumber())
            return 1;
        else
            return -1;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getPagenumber() { return pagenumber;}

    public void setPagenumber(int pagenumber) {this.pagenumber = pagenumber; }

    public int getPageArrayNumber() {
        return pageArrayNumber;
    }

    public void setPageArrayNumber(int pageArrayNumber) {
        this.pageArrayNumber = pageArrayNumber;
    }

    public int getPublishedArrayNumber() {
        return publishedArrayNumber;
    }

    public void setPublishedArrayNumber(int publishedArrayNumber) {
        this.publishedArrayNumber = publishedArrayNumber;
    }

    public int getDayOfWeekCreated() {
        return dayOfWeekCreated;
    }

    public void setDayOfWeekCreated(int dayOfWeekCreated) {
        this.dayOfWeekCreated = dayOfWeekCreated;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }
}
