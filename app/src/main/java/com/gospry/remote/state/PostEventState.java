package com.gospry.remote.state;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.gospry.AppCtx;
import com.gospry.generate.Event;
import com.gospry.remote.HttpMethod;
import com.gospry.remote.RemoteRequest;
import com.gospry.remote.RemoteState;
import com.gospry.suggestion.Suggestion;
import com.gospry.suggestion.SuggestionTypes;

import java.util.List;

/**
 * Created by rich on 21.11.14.
 */
public class PostEventState extends RemoteState {

    private final List<Suggestion> selectionList;
    private final Event event;

    public PostEventState(AppCtx ctx, List<Suggestion> tags, Event event) {
        super(ctx, true);
        this.selectionList = tags;
        this.event = event;
    }

    @Override
    public RemoteRequest prepare() {
        RemoteRequest request = new RemoteRequest(HttpMethod.POST, "/happening/", this);

        JsonObject object = new JsonObject();
        // {"start_time":"1415827547072",
        //  "description":"1234",
        //  "duration":"180",
        //  "max_attending":"5",
        //  "min_attending":"0",
        //  "price":"0",
        //  "description":"Das ist bis zu 140 Zeichen lang",
        //  "isPublic":"true",
        //  "keywords":["LULU","kw2"]}
        object.addProperty("start_time", event.getStartTime());
        object.addProperty("location", event.getLocation());
        object.addProperty("min_attending", event.getMinAttending());
        object.addProperty("isPublic", event.getIsPublic());
        object.addProperty("price", event.getPrice());
        object.addProperty("description", event.getDescription());
        object.addProperty("duration", event.getDuration());
        JsonArray keyWords = new JsonArray();
        int count = 0;
        for (Suggestion sel : selectionList) {
            keyWords.add(new JsonPrimitive(sel.getValue()));
            if (sel.getType() == SuggestionTypes.TAG) {
                keyWords.add(new JsonPrimitive(sel.getValue()));
            }
        }
        object.addProperty("max_attending", event.getMaxAttending());
        object.add("keywords", keyWords);
        request.setBody(object.toString());
        return request;
    }
}
