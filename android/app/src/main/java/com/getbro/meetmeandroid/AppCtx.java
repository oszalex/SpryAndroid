package com.getbro.meetmeandroid;

import android.content.Context;

import com.getbro.meetmeandroid.generate.Account;
import com.getbro.meetmeandroid.remote.RemoteRequest;
import com.getbro.meetmeandroid.remote.RemoteState;
import com.getbro.meetmeandroid.util.UNSAFEHttpClient;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
        client = new UNSAFEHttpClient(app); // XXX try to setup your certificate right
        host = new HttpHost("api.gospry.com", 443, "https");
    }



    public boolean isAuthenticated() {
        Account settings = application.getAccount();
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
