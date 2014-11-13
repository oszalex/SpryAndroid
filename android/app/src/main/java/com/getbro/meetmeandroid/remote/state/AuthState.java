package com.getbro.meetmeandroid.remote.state;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.remote.HttpMethod;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;

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
        return new RemoteRequest(HttpMethod.GET, "/activate/"+phoneNumber+"/"+token, this);
    }

    @Override
    public void onRequestOk(RemoteResponse response) {

        System.out.println("ok");
    }

    @Override
    public void onRequestFailed(RemoteResponse response) {

    }
}
