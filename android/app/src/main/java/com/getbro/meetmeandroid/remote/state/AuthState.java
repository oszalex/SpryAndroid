package com.getbro.meetmeandroid.remote.state;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.generate.Account;
import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.remote.HttpMethod;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;
import com.google.gson.JsonObject;

/**
 * Created by rich on 10.11.14.
 */
public class AuthState extends RemoteState {

    private final String token;
    private final String phoneNumber;

    public AuthState(String phoneNumber, String token, AppCtx ctx) {
        super(ctx);
        this.token = token;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public RemoteRequest invoke() {
        return new RemoteRequest(HttpMethod.POST, "/activate/"+phoneNumber+"/"+token, this);
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
