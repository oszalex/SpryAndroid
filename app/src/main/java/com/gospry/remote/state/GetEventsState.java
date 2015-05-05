package com.gospry.remote.state;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gospry.AppCtx;
import com.gospry.generate.Event;
import com.gospry.remote.HttpMethod;
import com.gospry.remote.RemoteRequest;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;
import com.gospry.remote.serialize.EventSerializer;

/**
 * Created by rich on 15.11.14.
 */
public class GetEventsState extends RemoteState {

    public GetEventsState(AppCtx ctx) {
        super(ctx, true);
    }

    @Override
    public RemoteRequest prepare() {
        return new RemoteRequest(HttpMethod.GET, "/happening", this);
    }

    @Override
    public void onRequestOk(RemoteResponse response) {
        JsonArray array = response.getJsonArray();

        session.queryEvents().deleteAll();
        session.queryKeywords().deleteAll();

        EventSerializer serializer = new EventSerializer();
        for (int i = 0; i < array.size(); i++) {
            JsonObject array2 = array.get(i).getAsJsonObject();
            Event event = serializer.deserialize(array2.get("happening").getAsJsonObject());
            String status = array2.get("status").toString();
            status = status.replace("\"", "").toUpperCase();
            event.setInvitationstatus(status);
            app.getSession().saveEvent(event);

            int a = 5;
       /*     JsonArray keywords = object.getAsJsonArray("keywords");
            for (int j = 0; j < keywords.size(); j++) {
                String text = keywords.get(j).getAsString();
                Keyword keyword = new Keyword();
                keyword.setEventId(event.getId());
                keyword.setText(text);
                app.getSession().saveKeyword(keyword);
            }*/
        }
       /* for (int i = 0; i < 40; i++) {
            Event event = new Event();
            event.setDescription("index " + i);
            app.getSession().saveEvent(event);
        }
        */
    }

    @Override
    public void onRequestFailed(RemoteResponse response) {
        super.onRequestFailed(response);
    }
}
