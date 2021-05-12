package com.example.serviceObject;

public class Login {
    private String userID;
    private String userPWD;

    public Login() {
    }


    public void set_userID(String userID) {
        this.userID = userID;
    } 

    public void set_userPWD(String userPWD) {
        this.userPWD = userPWD;
    }

    public String get_userID() {
        return this.userID;
    }

    public String get_userPWD() {
        return this.userPWD;
    }


    @Override
    public String toString() {
        return "Login [userID=" + userID + ", userPWD=" + userPWD + "]";
    }


    
}
