package com.gospry.remote.serialize;

import com.google.gson.JsonObject;
import com.gospry.generate.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rich on 13.11.14.
 */
public class EventSerializer extends BaseSerializer<Event> {

    @Override
    protected Map<String, String> getRemoteResolutionMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("description", "mDescription");
        map.put("start_time", "mStartTime");
        map.put("duration", "mDuration");
        map.put("isPublic", "mIsPublic");
        map.put("max_attending", "mMaxAttending");
        map.put("min_attending", "mMinAttending");
        map.put("price", "mPrice");
        map.put("id", "mRemoteId");
        map.put("creatorID", "mUser");
        //map.put("location","mLocation");
        return map;
    }

    @Override
    protected Event newObject() {
        return new Event();
    }

    @Override
    public void onSerialize(Event target, JsonObject data) {

    }

    @Override
    public void onDeserialize(Event target, JsonObject data) {

    }
}
