package com.getbro.bro.Model;

import android.content.Context;

import com.getbro.bro.Json.Event;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by chris on 04/05/14.
 */


public class EventModel extends SugarRecord<EventModel> {

    public Long CreatorId;
    public Date DateTime;
    public String Name;
    public Long[] Participants;
    public boolean IsPublic;
    public String[] Tags;
    public Long VenueId;

    public EventModel(Context ctx, Long CreatorId, Date DateTime, String Name, Long[] Participants,
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

    public static EventModel fromEvent(Context ctx, Event event){
        EventModel e = new EventModel(
                ctx,
                event.CreatorId,
                event.DateTime,
                event.Name,
                event.Participants,
                event.IsPublic,
                event.Tags,
                event.VenueId
            );

        e.save();

        return e;
    }

    public static EventModel updateOrInsert (Context ctx, Event event){
        if (event == null) return null;

        EventModel e = EventModel.findById(EventModel.class, event.Id);

        if(null == e)
            e = EventModel.fromEvent(ctx, event);

        return e;
    }
}
