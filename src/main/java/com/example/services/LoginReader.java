package com.example.services;

import java.io.InputStream;

import com.example.nettool.Reader;
import com.example.serviceObject.Login;

public class LoginReader extends Reader{
    Login login;
    public LoginReader(InputStream inputStream) {
        super(inputStream);
        login = new Login();
    }


    public void readBody() {
        try{
            login.set_userID(readString());
            login.set_userPWD(readString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Login get_Login() {
        return login;
    }


}
