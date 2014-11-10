package com.getbro.meetmeandroid.remote.state;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;

/**
 * Created by rich on 10.11.14.
 */
public class AuthState extends RemoteState {

    public AuthState(AppCtx ctx) {
        super(ctx);
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
