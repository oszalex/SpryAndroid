package com.getbro.bro.Json;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class User extends Item {

    public User(String Sex, String UserName, int[] Followed, int[] Follower){
        this.Sex = Sex;
        this.UserName = UserName;
        this.Followed = Followed;
    }

    @SerializedName("sex")
    public String Sex;

    @SerializedName("id")
    public Long Id;

    @SerializedName("username")
    public String UserName;

    @SerializedName("followed")
    public int[] Followed;

    @SerializedName("follower")
    public int[] Follower;

    @Override
    public String toString() {
        return UserName;
    }


}
