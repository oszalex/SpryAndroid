package com.getbro.meetme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chris on 18/07/14.
 */

public class EventItem {
    public long RemoteId;
    public Date DateTime;
    public String Name;
    public ArrayList<Long> Participants;
    public Boolean IsPublic;
    public ArrayList<String> Tags;
    public long VenueId;
    public long CreatorId;
    private int id;


    public EventItem(long CreatorId, Date DateTime, String Name, List<Long> Participants,
                     boolean IsPublic, List<String> Tags, long VenueId) {
        this.CreatorId = CreatorId;
        this.DateTime = DateTime;
        this.Name = Name;
        this.Participants = new ArrayList<Long>(Participants);
        this.IsPublic = IsPublic;
        this.Tags = new ArrayList<String>(Tags);
        this.VenueId = VenueId;
    }


}