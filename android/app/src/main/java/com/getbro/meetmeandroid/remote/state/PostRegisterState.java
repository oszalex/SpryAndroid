package com.getbro.meetmeandroid.remote.state;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.remote.HttpMethod;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;

import java.util.Collections;

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
        return new RemoteRequest(HttpMethod.POST, "/register/"+phoneNumber, this);
    }


    @Override
    public void onRequestOk(RemoteResponse response) {

    }

    @Override
    public void onRequestFailed(RemoteResponse response) {

    }
}
