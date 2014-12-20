package com.gospry.remote.state;

import com.google.gson.JsonObject;
import com.gospry.AppCtx;
import com.gospry.generate.Account;
import com.gospry.generate.LocalSession;
import com.gospry.remote.HttpMethod;
import com.gospry.remote.RemoteRequest;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;

/**
 * Created by rich on 10.11.14.
 */
public class PostAuthState extends RemoteState {

    private final String token;
    private final String phoneNumber;


    public PostAuthState(String phoneNumber, String token, AppCtx ctx) {
        super(ctx);
        this.token = token;
        this.phoneNumber = phoneNumber;

    }

    @Override
    public RemoteRequest prepare() {
        RemoteRequest request = new RemoteRequest(HttpMethod.POST, "/activate/" + phoneNumber + "/" + token, this);
        return request;
    }

    @Override
    public void onRequestOk(RemoteResponse response) {
        JsonObject object = response.getJsonObject();

        String password = object.get("password").getAsString();

        LocalSession session = app.getSession();
        Account settings = new Account();
        settings.setSecret(password);
        settings.setNumber(phoneNumber);
        session.saveAccount(settings);
    }

    @Override
    public void onRequestFailed(RemoteResponse response) {

    }
}
