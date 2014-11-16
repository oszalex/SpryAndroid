package com.getbro.meetmeandroid.remote.serialize;

import com.getbro.meetmeandroid.generate.Event;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rich on 13.11.14.
 */
public class EventSerializer extends BaseSerializer<Event> {

    @Override
    protected Map<String, String> getRemoteResolutionMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("description","mDescription");
        map.put("start_time","mStartTime");
        map.put("duration","mDuration");
        map.put("isPublic","mIsPublic");
        map.put("max_attending","mMaxAttending");
        map.put("min_attending","mMinAttending");
        map.put("price","mPrice");
        return map;
    }

    @Override
    protected Event newObject() {
        return new Event();
    }
}
