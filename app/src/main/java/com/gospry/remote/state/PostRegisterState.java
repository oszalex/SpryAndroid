package com.gospry.remote.state;

import com.google.gson.JsonObject;
import com.gospry.AppCtx;
import com.gospry.remote.HttpMethod;
import com.gospry.remote.RemoteRequest;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;

/**
 * Created by rich on 10.11.14.
 */
public class PostRegisterState extends RemoteState {

    private final String authID;
    private String phoneNumber;

    public PostRegisterState(String phoneNumber, AppCtx context, String authID) {
        super(context);
        this.phoneNumber = phoneNumber;
        this.authID = authID;
    }

    @Override
    public RemoteRequest prepare() {
        RemoteRequest request = new RemoteRequest(HttpMethod.POST, "/register/" + phoneNumber, this);
        JsonObject jason = new JsonObject();
        jason.addProperty("authID", authID);
        request.setBody(jason.toString());
        return request;
    }


    @Override
    public void onRequestOk(RemoteResponse response) {

    }

    @Override
    public void onRequestFailed(RemoteResponse response) {

    }
}
