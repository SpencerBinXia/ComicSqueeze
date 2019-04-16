package com.comicsqueeze.comicsqueeze.object;
import java.util.ArrayList;

public class Member {
    private String username;
    private String email;
    private String bio;
    private boolean admin;
    private String imgUrl;

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
}
