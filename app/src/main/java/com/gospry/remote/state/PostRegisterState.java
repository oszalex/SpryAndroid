package com.gospry.remote.state;

import com.gospry.AppCtx;
import com.gospry.remote.HttpMethod;
import com.gospry.remote.RemoteRequest;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;

/**
 * Created by rich on 10.11.14.
 */
public class PostRegisterState extends RemoteState {

    private String phoneNumber;

    public PostRegisterState(String phoneNumber, AppCtx context) {
        super(context);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public RemoteRequest prepare() {
        return new RemoteRequest(HttpMethod.POST, "/register/" + phoneNumber, this);
    }


    @Override
    public void onRequestOk(RemoteResponse response) {

    }

    @Override
    public void onRequestFailed(RemoteResponse response) {

    }
}
