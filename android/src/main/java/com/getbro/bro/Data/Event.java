package com.getbro.bro.Data;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chris on 04/05/14.
 */

@DatabaseTable
public class Event implements Serializable {

    @DatabaseField(generatedId=true)
    private int id;

    @SerializedName("id")
    @DatabaseField
    public long RemoteId;

    @SerializedName("datetime")
    @DatabaseField
    public Date DateTime;

    @SerializedName("name")
    @DatabaseField
    public String Name;

    @SerializedName("participant_ids")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public long[] Participants;

    @SerializedName("public")
    @DatabaseField
    public Boolean IsPublic;

    @SerializedName("tags")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public String[] Tags;

    @SerializedName("venue_id")
    @DatabaseField
    public long VenueId;

    @SerializedName("creator_id")
    @DatabaseField
    public long CreatorId;

    public Event(){}

    public Event(long CreatorId, Date DateTime, String Name, long[] Participants,
                 boolean IsPublic, String[] Tags, long VenueId){
        this.CreatorId = CreatorId;
        this.DateTime = DateTime;
        this.Name = Name;
        this.Participants = Participants;
        this.IsPublic = IsPublic;
        this.Tags = Tags;
        this.VenueId = VenueId;
    }

}
