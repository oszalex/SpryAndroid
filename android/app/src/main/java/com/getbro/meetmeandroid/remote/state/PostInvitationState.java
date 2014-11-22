package com.getbro.meetmeandroid.remote.state;

import android.widget.Toast;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.generate.Event;
import com.getbro.meetmeandroid.remote.HttpMethod;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;
import com.google.gson.JsonObject;

/**
 * Created by rich on 22.11.14.
 */
public class PostInvitationState extends RemoteState {

    private Event event;

    public PostInvitationState(AppCtx ctx, Event event) {
        super(ctx, true);
        this.event = event;
    }

    @Override
    public RemoteRequest prepare() {
        RemoteRequest request = new RemoteRequest(HttpMethod.PUT, "/invitation/" + event.getRemoteId(), this);

        JsonObject object = new JsonObject();
        object.addProperty("status", event.getAcceptState());
        request.setBody(object.toString());

        return request;
    }

    @Override
    public void onRequestOk(RemoteResponse response) {
        Toast.makeText(app, "invitation sent to remote host: " + event.getAcceptState(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestFailed(RemoteResponse response) {
        super.onRequestFailed(response);
    }
}
