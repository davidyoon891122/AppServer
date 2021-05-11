package com.example.services;

import com.example.nettool.SocketReader;

public class LoginServiceQuery extends SocketReader implements ServiceInterface {
    private String userID;
    private String userPWD;
    



    @Override
    public void readBody() {
        // TODO Auto-generated method stub
        try{
            userID = readString();
            userPWD = readString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
}
