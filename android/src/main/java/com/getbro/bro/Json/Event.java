package com.getbro.bro.Json;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event extends Item {

    @SerializedName("creator_id")
    public Long CreatorId;

    @SerializedName("datetime")
    public Date DateTime;

    @SerializedName("name")
    public String Name;

    @SerializedName("participant_ids")
    public Long[] Participants;

    @SerializedName("public")
    public boolean IsPublic;

    @SerializedName("tags")
    public String[] Tags;

    @SerializedName("venue_id")
    public Long VenueId;
}
