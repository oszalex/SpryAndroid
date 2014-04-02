package com.getbro.bro.Json;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event extends Item {

    @SerializedName("creator_id")
    public int CreatorId;

    @SerializedName("datetime")
    public Date DateTime;

    @SerializedName("name")
    public String Name;

 //   @SerializedName("participant_ids")
 //   public int[] Participants;

    @SerializedName("public")
    public boolean IsPublic;

    @SerializedName("tags")
    public Tag[] Tags;

    @SerializedName("venue_id")
    public int VenueId;
}
