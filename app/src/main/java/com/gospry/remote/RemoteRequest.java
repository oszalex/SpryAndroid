package com.gospry.remote;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.gospry.AppCtx;
import com.gospry.util.C;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

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
    private String body = null;

    public RemoteRequest(HttpMethod method, String route, RemoteState state) {
        this.method = method;
       // this.route = "/" + C.SERVER_VERSION + route;
        this.route =  route ;
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
        //TODO: Fix in BAckend the header Problem
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setHeader("Accept", "application/json");

        if (body != null) {
            HttpEntityEnclosingRequestBase req = (HttpEntityEnclosingRequestBase) httpRequest;
            try {
                StringEntity entity = new StringEntity(body);
                req.setEntity(entity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {
            final HttpHost host = context.getHost();
            final HttpClient client = context.getClient();
            Log.d("HTTP", "request: " + httpRequest.getRequestLine().toString());
            //TODO: rausnehmen
            if (httpRequest instanceof HttpEntityEnclosingRequest) { //test if request is a POST
                HttpEntity entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
                String body = EntityUtils.toString(entity); //here you have the POST body
            }
            Log.d("HTTP", "requestbody: " + body);
            HttpResponse httpResponse = client.execute(host, httpRequest);
            Log.d("HTTP", "response: " + httpResponse.getStatusLine().toString());

            return new RemoteResponse(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String basicAuthBase64(String basicAuth) {
        return Base64.encodeToString(basicAuth.getBytes(), Base64.DEFAULT).trim();
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
