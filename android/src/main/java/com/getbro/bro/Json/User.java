package com.getbro.bro.Json;

import com.google.gson.annotations.SerializedName;

public class User  extends Item {

    public User(String Sex, String UserName, String[] Followed){
        this.Sex = Sex;
        this.UserName = UserName;
        this.Followed = Followed;
    }

    @SerializedName("sex")
    public String Sex;

    @SerializedName("username")
    public String UserName;

    @SerializedName("followed")
    public String[] Followed;

    @Override
    public String toString() {
        return UserName;
    }


}
