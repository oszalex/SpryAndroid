package com.getbro.meetmeandroid.remote.serialize;

import com.getbro.meetmeandroid.generate.Event;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rich on 13.11.14.
 */
public class EventSerializer extends BaseSerializer<Event> {

    public EventSerializer(Event target) {
        super(target);
    }

    @Override
    protected Map<String, String> getRemoteResolutionMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("description","description");
        return map;
    }
}
