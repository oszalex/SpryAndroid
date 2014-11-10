package com.getbro.meetmeandroid.remote;

import android.os.AsyncTask;

import com.getbro.meetmeandroid.AppCtx;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by rich on 10.11.14.
 */
public class RemoteRequest extends AsyncTask<Void, Void, Void> {

    private final String route;
    private final AppCtx context;
    private final HttpMethod method;
    private final RemoteState state;

    public RemoteRequest(HttpMethod method, String route, Map<String,String> bindings, RemoteState state) {
        this.method = method;
        this.route = route;
        this.context = state.context;
        this.state = state;
    }

    @Override
    protected Void doInBackground(Void... params) {

        HttpRequestBase httpRequest = method.freshRequest(route);

        try {
            final HttpHost host = context.getHost();
            final HttpClient client = context.getClient();
            HttpResponse httpResponse = client.execute(host, httpRequest);

            RemoteResponse response = new RemoteResponse(httpResponse);

            respond(response);
        } catch (IOException e) {
            respond(new RemoteResponse(null));
        }

        return null;
    }

    private void respond(RemoteResponse response) {
        RemoteCallback userCallback = state.getUserCallback();
        if (response.getStatusCode() == 200) {
            state.onRequestOk(response);
            if (userCallback != null) {
                userCallback.onRequestOk(response);
            }
        } else {
            state.onRequestFailed(response);
            if (userCallback != null) {
                userCallback.onRequestFailed(response);
            }
        }
    }


}
