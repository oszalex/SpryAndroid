package com.getbro.bro.Json;


import com.google.gson.annotations.SerializedName;

public class Invitation extends Item {

    @SerializedName("users_id")
    public int[] UserIds;

    @SerializedName("events_id")
    public int[] EventIds;

    @SerializedName("user")
    public User User;

    @SerializedName("attending")
    public AttendingStatus Attending;
}