package com.getbro.bro.Auth;

public class UserCredentials{
    public String username;
    public String password;

    private UserCredentials() {}

    public UserCredentials(String u, String p){
        this.username = u;
        this.password = p;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
