package com.gospry.remote.state;

import android.widget.Toast;

import com.google.gson.JsonObject;
import com.gospry.AppCtx;
import com.gospry.generate.Event;
import com.gospry.remote.HttpMethod;
import com.gospry.remote.RemoteRequest;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;

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
