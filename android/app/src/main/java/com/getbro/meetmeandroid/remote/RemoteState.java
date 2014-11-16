package com.getbro.meetmeandroid.remote;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.MeetMeApp;
import com.getbro.meetmeandroid.generate.LocalSession;

/**
 * Created by rich on 10.11.14.
 */
public abstract class RemoteState implements RemoteCallback {

    protected AppCtx context;
    protected MeetMeApp app;
    protected RemoteCallback userCallback;
    protected LocalSession session;
    protected boolean useAuth;

    public RemoteState(AppCtx ctx) {
        this(ctx, false);
    }

    public RemoteState(AppCtx ctx, boolean useAuth) {
        this.context = ctx;
        this.app = context.getApplication();
        this.session = app.getSession();
        this.useAuth = useAuth;
    }

    public abstract RemoteRequest invoke();

    public void setCallback(RemoteCallback callback) {
        this.userCallback = callback;
    }

    public RemoteCallback getUserCallback() {
        return userCallback;
    }
}
