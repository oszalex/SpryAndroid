package com.getbro.bro.Data;

import android.content.Context;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by chris on 04/05/14.
 */


public class Event extends SugarRecord<Event> {

    @SerializedName("id")
    public Long Id;

    @SerializedName("datetime")
    public Date DateTime;

    @SerializedName("name")
    public String Name;

    @SerializedName("participant_ids")
    public Long[] Participants;

    @SerializedName("public")
    public Boolean IsPublic;

    @SerializedName("tags")
    public String[] Tags;

    @SerializedName("venue_id")
    public Long VenueId;

    @SerializedName("creator_id")
    public Long CreatorId;

    public Event(Context ctx){
        super(ctx);
    }

    public Event(Context ctx, Long CreatorId, Date DateTime, String Name, Long[] Participants,
                 boolean IsPublic, String[] Tags, Long VenueId){
        super(ctx);
        this.CreatorId = CreatorId;
        this.DateTime = DateTime;
        this.Name = Name;
        this.Participants = Participants;
        this.IsPublic = IsPublic;
        this.Tags = Tags;
        this.VenueId = VenueId;
    }

    public static Event updateOrInsert (Context ctx, Event event){
        if (event == null) return null;

        Event e = Event.findById(Event.class, event.Id);

        if(null == e)
            event.save();

        return e;
    }
}
