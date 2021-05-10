package com.example.nettool;

public class TestQuery extends NetManagerQuery{
    private String userId;
    private String userPwd;

    public TestQuery() {
        set_process(001);
        set_service(001);
    }

    public void set_userId(String userId) {
        this.userId = userId;
    }

    public void set_userPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    
    public void willBuildDataBytes() {
        putStringData(userId);
        putStringData(userPwd);
    }

    @Override
    public String toString() {
        return "TestQuery [userId=" + userId + ", userPwd=" + userPwd + "]";
    }


    
}
