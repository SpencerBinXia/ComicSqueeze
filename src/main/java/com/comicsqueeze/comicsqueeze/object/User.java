package com.comicsqueeze.comicsqueeze.object;
import java.util.ArrayList;

public class User {
    private String username;
    private boolean active;
    private boolean admin;

    public String getUsername() {return username;}
    public Boolean getActiveStatus() {return active;}
    public Boolean getAdminStatus() {return admin;}

    public void setUsername(String name){this.username = name;}
    public void setActiveStatus(boolean active) {this.active = active;}
    public void setAdminStatus(boolean admin) {this.admin = admin;}
}
