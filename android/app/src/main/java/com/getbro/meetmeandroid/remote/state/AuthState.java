package com.getbro.meetmeandroid.remote.state;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;

/**
 * Created by rich on 10.11.14.
 */
public class AuthState extends RemoteState {

    private final String token;

    public AuthState(String phoneNumber, String token, AppCtx ctx) {
        super(ctx);
        this.token = token;
    }

    @Override
    public RemoteRequest invoke() {
        return null;
    }

    @Override
    public void onRequestOk(RemoteResponse response) {

    }

    @Override
    public void onRequestFailed(RemoteResponse response) {

    }
}
