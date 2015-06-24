package com.gospry.remote.state;

import com.google.gson.JsonObject;
import com.gospry.AppCtx;
import com.gospry.remote.HttpMethod;
import com.gospry.remote.RemoteRequest;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;

/**
 * Created by rich on 22.11.14.
 */
public class PostNewLocationState extends RemoteState {

    JsonObject location;
    String name;

    public PostNewLocationState(AppCtx ctx, JsonObject location, String name) {
        super(ctx, true);
        this.name = name;
        this.location = location;
    }

    @Override
    public RemoteRequest prepare() {


        RemoteRequest request = new RemoteRequest(HttpMethod.POST, "/location/get", this);
        JsonObject object = new JsonObject();
        object.addProperty("longitude", location.get("lon").getAsString());
        object.addProperty("latitude", location.get("lat").getAsString());
        object.addProperty("description", location.get("display_name").getAsString());
        object.addProperty("name", name);
        object.addProperty("publiclocation", 1);
        //   request.setBody("{\"longitude\":\"16.3583422142484\",\"latitude\":\"48.2031942\",\"description\":\"MQ, Neubau, Wien, 1070, Österreich\",\"name\":\"Museumsquartier\",\"publiclocation\":1}");
        String test = object.toString();
        request.setBody(object.toString());
        return request;
    }

    @Override
    public void onRequestOk(RemoteResponse response) {
        super.onRequestOk(response);
    }
}
