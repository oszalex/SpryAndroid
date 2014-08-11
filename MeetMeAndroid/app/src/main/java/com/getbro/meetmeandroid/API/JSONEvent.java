package com.getbro.meetmeandroid.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chris on 11/08/14.
 */
public class JSONEvent {
    public long createdAt;
    public long creatorId;

    @SerializedName("id")
    public long objectId;
    public String raw;
    public long time;

}
