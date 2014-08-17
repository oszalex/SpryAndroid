package com.getbro.meetmeandroid.API;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by chris on 11/08/14.
 */
public class JSONEvent {
    //public long createdAt;
    public long creatorId;
    public long eventId;
    public String raw;
    public int duration;
    public List<String> tags;
    public String location;

    @SerializedName("datetime")
    public String time;

}
