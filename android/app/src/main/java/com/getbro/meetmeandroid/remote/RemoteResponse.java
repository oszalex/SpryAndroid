package com.getbro.meetmeandroid.remote;

import org.apache.http.HttpResponse;

/**
 * Created by rich on 10.11.14.
 */
public class RemoteResponse {
    private final HttpResponse response;

    public RemoteResponse(HttpResponse httpResponse) {
        this.response = httpResponse;
    }

    public int getStatusCode() {
        if (response == null || response.getStatusLine() == null) {
            return -1;
        } else {
            return response.getStatusLine().getStatusCode();
        }
    }
}
