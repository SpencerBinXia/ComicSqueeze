package com.comicsqueeze.comicsqueeze.object;

import java.util.Comparator;

public class Page implements Comparable<Page> {

    private String issue;
    private String imgurl;
    private int votes;
    private int pagenumber;
    private boolean published;
    private String username;
    private String series;
    private int pageArrayNumber;
    private int publishedArrayNumber;

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
}
