package com.getbro.bro.Auth;

public class UserAccount {
    public String username;
    public String password;
    public long id;

    private UserAccount() {}

    public UserAccount(String u, String p){
        this.username = u;
        this.password = p;
    }

    public void setId(long id){
        this.id = id;
    }

    public Long getId(){ return id; }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
