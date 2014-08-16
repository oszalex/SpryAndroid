package com.getbro.meetmeandroid.API;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by chris on 11/08/14.
 */
public class JSONEvent {
    public long createdAt;
    public long creatorId;

    public long eventId;
    public String raw;

    @SerializedName("datetime")
    public String time;

}
