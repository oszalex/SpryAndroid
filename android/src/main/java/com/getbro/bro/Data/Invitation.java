package com.getbro.bro.Data;


import com.google.gson.annotations.SerializedName;

public class Invitation {

    @SerializedName("Id")
    public Long Id;

    @SerializedName("users_id")
    public Long[] UserIds;

    @SerializedName("events_id")
    public Long[] EventIds;

    @SerializedName("user")
    public com.getbro.bro.Data.User User;

    @SerializedName("attending")
    public AttendingStatus Attending;
}