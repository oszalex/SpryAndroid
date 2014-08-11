package com.getbro.meetmeandroid.API;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chris on 28/07/14.
 */
public class APIEvent {
    private long creatorID;
    private String raw;
    private Date time;

    public APIEvent(long creatorID, String raw, Date time){
        this.creatorID = creatorID;
        this.raw = raw;
        this.time = time;
    }

    public long getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(long creatorID) {
        this.creatorID = creatorID;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public static ArrayList<APIEvent> fromJSONEvent(List<JSONEvent> items){
        ArrayList<APIEvent> results = new ArrayList<APIEvent>();

        for(JSONEvent i : items){
            results.add(new APIEvent(i.creatorId,i.raw,new java.util.Date(i.time)));
        }

        return results;
    }
}
