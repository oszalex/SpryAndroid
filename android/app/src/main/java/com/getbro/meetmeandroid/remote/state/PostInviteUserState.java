package com.getbro.meetmeandroid.remote.state;

import android.widget.Toast;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.remote.HttpMethod;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;

/**
 * Created by Alex on 03.12.2014.
 */
//TODO: Send User Invitation to Server, test if this really works
public class PostInviteUserState extends RemoteState {
    private long eventid;
    private long userid;

    public PostInviteUserState(AppCtx ctx, long event, long user) {
        super(ctx, true);
        this.eventid = event;
        this.userid = user;
    }

    @Override
    public RemoteRequest prepare() {
        RemoteRequest request = new RemoteRequest(HttpMethod.POST, "/invitation/" + Long.toString(eventid) + "/" + Long.toString(userid), this);
/*
        JsonObject object = new JsonObject();
        object.addProperty("status", event.getAcceptState());
        request.setBody(object.toString());
*/
        return request;
    }

    @Override
    public void onRequestOk(RemoteResponse response) {
        Toast.makeText(app, "User invited: " + Long.toString(userid), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestFailed(RemoteResponse response) {
        super.onRequestFailed(response);
    }
}
