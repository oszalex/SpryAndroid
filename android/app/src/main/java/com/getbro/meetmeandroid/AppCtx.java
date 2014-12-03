package com.getbro.meetmeandroid;

import com.getbro.meetmeandroid.generate.Account;
import com.getbro.meetmeandroid.remote.MySSLSocketFactory;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteState;

import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

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
        //  DEBUG
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
        HttpProtocolParams.setUseExpectContinue(params, true);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        try {
            schReg.register(new Scheme("https", new MySSLSocketFactory(null), 443));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ClientConnectionManager conMgr = new SingleClientConnManager(params, schReg);

        client = new DefaultHttpClient(conMgr, params);
        host = new HttpHost("api.gospry.com", 443, "https");
    }

    public boolean isAuthenticated() {
        Account settings = application.getAccount();
        return settings != null && !"".equals(settings.getNumber());
    }

    public void invoke(RemoteState state) {
        RemoteRequest request = state.prepare();
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
