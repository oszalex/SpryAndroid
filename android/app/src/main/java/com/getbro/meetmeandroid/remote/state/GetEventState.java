package com.getbro.meetmeandroid.remote.state;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.generate.Event;
import com.getbro.meetmeandroid.remote.HttpMethod;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;

/**
 * Created by rich on 22.11.14.
 */
public class GetEventState extends RemoteState {
    private Long eventId;

    public GetEventState(AppCtx ctx, Long eventId) {
        super(ctx, true);
        this.eventId = eventId;
    }

    @Override
    public RemoteRequest prepare() {
        return new RemoteRequest(HttpMethod.GET, "/happening/"+eventId, this);
    }

    @Override
    public void onRequestOk(RemoteResponse response) {
        super.onRequestOk(response);
    }
}
