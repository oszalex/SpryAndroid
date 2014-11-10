package com.getbro.meetmeandroid.remote.coord;

import com.getbro.meetmeandroid.AppCtx;
import com.getbro.meetmeandroid.remote.RemoteCallback;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rich on 10.11.14.
 */
public class SequenceState extends RemoteState {

    private List<RemoteState> stateList;
    private RemoteCallback stolenCallback;

    public SequenceState(AppCtx ctx, RemoteState ... stateList) {
        super(ctx);
        this.stateList = Arrays.asList(stateList);
        stolenCallback = userCallback;
        userCallback = null; // steal it and invoke it after the sequence is executed
    }

    @Override
    public RemoteRequest invoke() {
        return invokeNext().invoke();
    }

    public RemoteState invokeNext() {
        if (stateList.size() > 0) {
            RemoteState state = stateList.remove(0);
            state.setCallback(this);
            return state;
        }
        return null;
    }

    @Override
    public void onRequestOk(RemoteResponse response) {
        RemoteRequest request = invokeNext().invoke();
        if (request == null) {
            stolenCallback.onRequestOk(response);
        } else {
            request.execute();
        }
    }

    @Override
    public void onRequestFailed(RemoteResponse response) {
        stolenCallback.onRequestFailed(response);
    }
}
