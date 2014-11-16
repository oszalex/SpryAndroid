package com.getbro.meetmeandroid.remote.state;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.generate.Event;
import com.getbro.meetmeandroid.remote.HttpMethod;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;
import com.getbro.meetmeandroid.remote.serialize.EventSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedList;

/**
 * Created by rich on 15.11.14.
 */
public class EventsGetState extends RemoteState {

    public EventsGetState(AppCtx ctx) {
        super(ctx, true);
    }

    @Override
    public RemoteRequest invoke() {
        return new RemoteRequest(HttpMethod.GET, "/happening", this);
    }

    @Override
    public void onRequestOk(RemoteResponse response) {
        JsonArray array = response.getJsonArray();

        session.queryEvents().deleteAll();

        EventSerializer serializer = new EventSerializer();
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            Event event = serializer.deserialize(object);
            app.getSession().saveEvent(event);
            event.setId(null);
            app.getSession().saveEvent(event);
            event.setId(null);
            app.getSession().saveEvent(event);
        }
    }

    @Override
    public void onRequestFailed(RemoteResponse response) {

    }
}
