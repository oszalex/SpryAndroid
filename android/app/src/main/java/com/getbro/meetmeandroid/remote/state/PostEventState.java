package com.getbro.meetmeandroid.remote.state;

import android.text.Selection;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.remote.HttpMethod;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;
import com.getbro.meetmeandroid.suggestion.Suggestion;
import com.getbro.meetmeandroid.suggestion.SuggestionTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.List;

/**
 * Created by rich on 21.11.14.
 */
public class PostEventState extends RemoteState {

    private final List<Suggestion> selectionList;

    public PostEventState(AppCtx ctx, List<Suggestion> tags) {
        super(ctx, true);
        this.selectionList = tags;
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
        object.addProperty("start_time", DateTime.now().getMillis()+40*100*20000);
        object.addProperty("min_attending", 0);
        object.addProperty("isPublic", true);
        object.addProperty("price", 0);
        object.addProperty("description", "desc...");
        object.addProperty("duration", 180);
        JsonArray keyWords = new JsonArray();
        int count = 0;
        for (Suggestion sel : selectionList) {
            keyWords.add(new JsonPrimitive(sel.getValue()));
            if (sel.getType() == SuggestionTypes.PERSON) {
                count++;
            }
        }
        object.addProperty("max_attending", count);
        object.add("keywords", keyWords);
        request.setBody(object.toString());
        return request;
    }
}
