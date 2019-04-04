package com.comicsqueeze.comicsqueeze.object;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;
    private boolean active;
    private boolean admin;

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    public Boolean getActiveStatus() {return active;}
    public Boolean getAdminStatus() {return admin;}

    public void setUsername(String name){this.username = name;}
    public void setPassword(String pass){this.password = pass;}
    public void setEmail(String email) {this.email = email;}
    public void setActiveStatus(boolean active) {this.active = active;}
    public void setAdminStatus(boolean admin) {this.admin = admin;}
}
