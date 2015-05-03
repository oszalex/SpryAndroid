package com.gospry.remote.state;

import com.gospry.AppCtx;
import com.gospry.remote.HttpMethod;
import com.gospry.remote.RemoteRequest;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;

/**
 * Created by rich on 22.11.14.
 */
public class GetEventInvitationState extends RemoteState {
    private Long eventId;

    public GetEventInvitationState(AppCtx ctx, Long eventId) {
        super(ctx, true);
        this.eventId = eventId;
    }

    @Override
    public RemoteRequest prepare() {
        return new RemoteRequest(HttpMethod.GET, "/happening/" + eventId + "/invited", this);
    }

    @Override
    public void onRequestOk(RemoteResponse response) {
        super.onRequestOk(response);
    }
}
