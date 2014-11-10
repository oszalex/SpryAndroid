package com.getbro.meetmeandroid.API;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by chris on 28/07/14.
 */
public class APIEvent {
    private final static String TAG = APIEvent.class.toString();
    private long creatorID;
    private String raw;
    private Date time;
    private int duration;
    private List<String> tags;
    private boolean stared = false;
    private String location;

    public APIEvent(long creatorID, String raw, Date time, List<String> tags, String location){
        this.creatorID = creatorID;
        this.raw = raw;
        this.time = time;
        this.duration = 120;
        this.tags = tags;
        this.location = location;
    }

    public APIEvent(long creatorID, String raw, Date time, List<String> tags, String location, int duration ){
        this.creatorID = creatorID;
        this.raw = raw;
        this.time = time;
        this.duration = duration;
        this.tags = tags;
        this.location = location;
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

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

            try {
                results.add(new APIEvent(i.creatorId, i.raw, sdf.parse(i.time), Arrays.asList(i.tags), i.location, i.duration));
            } catch (ParseException e) {
                Log.v(TAG, "could not parse timestamp");
            }

        }

        return results;
    }

    @Override
    public String toString() {
        return raw;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public boolean isStared() {
        return stared;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getLocation() {
        return location;
    }
}
