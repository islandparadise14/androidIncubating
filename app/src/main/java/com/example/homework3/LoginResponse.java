package com.example.homework3;

public class LoginResponse {
    private Result result;
    private Auth auth;
    private User user;

    public Result getResult(){
        return result;
    }

    public Auth getAuth() {
        return auth;
    }

    public User getUser() {
        return user;
    }
}
