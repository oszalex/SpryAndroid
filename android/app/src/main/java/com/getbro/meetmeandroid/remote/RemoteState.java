package com.getbro.meetmeandroid.remote;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.MeetMeApp;

/**
 * Created by rich on 10.11.14.
 */
public abstract class RemoteState implements RemoteCallback {

    protected AppCtx context;
    protected MeetMeApp app;
    protected RemoteCallback userCallback;

    public RemoteState(AppCtx ctx) {
        context = ctx;
    }

    public abstract RemoteRequest invoke();

    public void setCallback(RemoteCallback callback) {
        this.userCallback = callback;
    }

    public RemoteCallback getUserCallback() {
        return userCallback;
    }
}
