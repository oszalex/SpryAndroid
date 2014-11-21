package com.getbro.meetmeandroid.remote;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.getbro.meetmeandroid.AppCtx;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by rich on 10.11.14.
 */
public class RemoteRequest extends AsyncTask<Void, Void, RemoteResponse> {

    private final String route;
    private final AppCtx context;
    private final HttpMethod method;
    private final RemoteState state;
    private String body;

    public RemoteRequest(HttpMethod method, String route, RemoteState state) {
        this.method = method;
        this.route = "/v2" + route;
        this.context = state.context;
        this.state = state;
    }

    public void setBody(String s) {
        this.body = s;
    }

    @Override
    protected RemoteResponse doInBackground(Void... params) {

        HttpRequestBase httpRequest = method.freshRequest(route);

        if (state.useAuth) {
            httpRequest.setHeader("Authorization", "Basic " + basicAuthBase64(context.getApplication().getBasicAuth()));
        }

        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setHeader("Accept", "*/*");

        if (body != null) {
            HttpPost req = (HttpPost)httpRequest;
            try {
                StringEntity entity = new StringEntity(body);
                req.setEntity(new ByteArrayEntity(body.getBytes()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {
            final HttpHost host = context.getHost();
            final HttpClient client = context.getClient();
            Log.d("HTTP", "request: " + httpRequest.getRequestLine().toString());
            HttpResponse httpResponse = client.execute(host, httpRequest);

            return new RemoteResponse(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String basicAuthBase64(String basicAuth) {
        return Base64.encodeToString(basicAuth.getBytes(), Base64.DEFAULT);
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

    @Override
    protected void onPostExecute(RemoteResponse response) {
        if (response == null) {
            response = new RemoteResponse(null);
        }
        respond(response);
    }
}
