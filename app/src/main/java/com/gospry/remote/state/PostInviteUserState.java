package com.gospry.remote.state;

import android.widget.Toast;

import com.google.gson.JsonObject;
import com.gospry.AppCtx;
import com.gospry.remote.HttpMethod;
import com.gospry.remote.RemoteRequest;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;

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

        JsonObject object = new JsonObject();
        //  object.addProperty("status", event.getAcceptState());
        object.addProperty("status", "INVITED");

        request.setBody(object.toString());

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
