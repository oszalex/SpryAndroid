package com.gospry.remote;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.gospry.AppCtx;

import org.apache.http.Header;
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
        this.route = route;
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
        if (body != null) {
            HttpEntityEnclosingRequestBase req = (HttpEntityEnclosingRequestBase) httpRequest;
            try {
                //TODO: Reminder: Charset wichtig!! sonst gibts fehler im backend bei Umlaut - Fehler stundenlang gesucht
                StringEntity entity = new StringEntity(body, "UTF-8");
                req.setEntity(entity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {
            final HttpHost host = context.getHost();
            final HttpClient client = context.getClient();
            Log.d("HTTP", "request: " + httpRequest.getRequestLine().toString());
            // rausgenommen just debug
            HttpEntity entity = null;

            if (httpRequest instanceof HttpEntityEnclosingRequest && ((HttpEntityEnclosingRequest) httpRequest).getEntity() != null) { //test if request is a POST
                entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
                String body = EntityUtils.toString(entity); //here you have the POST body
            }
            Log.d("HTTP", "requestbody: " + body);
            //TODO: Fix in BAckend the header Problem
            httpRequest.setHeader("Content-Type", "application/json");
            httpRequest.setHeader("Accept", "*/*");
            Header[] test = httpRequest.getAllHeaders();
            //    httpRequest.setHeader("User-Agent", "curl/7.35.0");
            HttpResponse httpResponse;
            httpResponse = client.execute(host, httpRequest);

            ;
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
