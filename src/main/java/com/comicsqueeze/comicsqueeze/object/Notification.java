package com.comicsqueeze.comicsqueeze.object;

public class Notification {
    private String username;
    private String link;
    private String usernameto;
    private String body;
    private String type;
    private boolean read;
    private boolean adminRead;
    private String seriesReported;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUsernameto() {
        return usernameto;
    }

    public void setUsernameto(String usernameto) {
        this.usernameto = usernameto;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isAdminRead() {
        return adminRead;
    }

    public void setAdminRead(boolean adminRead) {
        this.adminRead = adminRead;
    }

    public String getSeriesReported() {
        return seriesReported;
    }

    public void setSeriesReported(String seriesReported) {
        this.seriesReported = seriesReported;
    }
}
