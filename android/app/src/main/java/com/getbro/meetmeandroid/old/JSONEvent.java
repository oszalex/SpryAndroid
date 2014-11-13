package com.getbro.meetmeandroid.old;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chris on 11/08/14.
 */
public class JSONEvent {
    //public long createdAt;
    public long creatorId;
    public long eventId;
    public String raw;
    public int duration;
    public String tags[];
    public String location;

    @SerializedName("datetime")
    public String time;

}
