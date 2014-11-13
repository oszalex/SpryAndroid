package com.getbro.meetmeandroid;

import com.getbro.meetmeandroid.generate.Account;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteState;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * rich
 * 11/1/14
 */
public class AppCtx {

    private final HttpHost host;
    private HttpClient client;
    private MeetMeApp application;

    public AppCtx(MeetMeApp app) {
        this.application = app;
        client = new DefaultHttpClient();
        host = new HttpHost("api.gospry.com", 8080, "http");
    }

    public boolean isAuthenticated() {
        Account settings = application.getSession().queryAccounts().first();
        return settings != null && !"".equals(settings.getNumber());
    }

    public void invoke(RemoteState state) {
        RemoteRequest request = state.invoke();
        if (request != null) {
            request.execute();
        }
    }

    public HttpHost getHost() {
        return host;
    }

    public HttpClient getClient() {
        return client;
    }

    public MeetMeApp getApplication() {
        return application;
    }
}
