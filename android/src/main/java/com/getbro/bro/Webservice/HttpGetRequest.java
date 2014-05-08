package com.getbro.bro.Webservice;

import android.accounts.AccountManager;
import android.app.Application;
import android.util.Log;

import com.getbro.bro.Auth.AuthManager;
import com.getbro.bro.Auth.UserAccount;
import com.getbro.bro.Data.*;
import com.getbro.bro.Data.Event;
import com.getbro.bro.Data.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Calendar;
import java.util.Date;


import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HttpGetRequest extends Application {
    private static final String TAG = HttpGetRequest.class.getSimpleName();
    private String webServiceUrl;
    private HttpClient client;

    private static HttpGetRequest instance;

    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

    public static HttpGetRequest getHttpGetRequest() {
        return instance;
    }


    public HttpGetRequest() { }

    public HttpGetRequest(String webServiceUrl, String userName, String password) {
        configureClient(webServiceUrl,userName,password);
    }

    public void configureClient(String webServiceUrl, String userName, String password) {
        this.webServiceUrl = webServiceUrl;
        this.client = getClient(userName,password);
    }
    public void configureClient(String userName, String password) {
        this.client = getClient(userName,password);
    }

    public User[] getAllUsers() {
        String json = getJson("/users");
        Data<User[]> data = gsonFactory().fromJson(json, new TypeToken<Data<User[]>>() {}.getType());
        return data.data;
    }

    public long[] getFriends() {
        String json = getJson("/users/me");
        Data<User> data = gsonFactory().fromJson(json, new TypeToken<Data<User>>() {}.getType());
        return data.data.Followed;
    }

    public User getUser(long id) {
        String json = getJson("/users/id/" + id);
        Data<User> data = gsonFactory().fromJson(json, new TypeToken<Data<User>>() {}.getType());
        return data.data;
    }

    public User getOwnUserElement() {
        String json = getJson("/users/me");
        Data<User> data = gsonFactory().fromJson(json, new TypeToken<Data<User>>() {}.getType());
        return data.data;
    }

    public User[] matchUser(String match) {
        String url = match.isEmpty() ? "/users/" : ("/ac/users/" + match);
        String json = getJson(url);
        Data<User[]> data = gsonFactory().fromJson(json, new TypeToken<Data<User[]>>() {}.getType());
        return data.data;
    }

    public Event[] getAllEvents() {
        String json = getJson("/events");
        Data<Event[]> data = gsonFactory().fromJson(json, new TypeToken<Data<Event[]>>() {}.getType());
        return data.data;
    }

    public Event getEvent(long id) {
        String json = getJson("/events/id/" + id);
        Data<Event> data = gsonFactory().fromJson(json, new TypeToken<Data<Event>>() {}.getType());
        return data.data;
    }

    public Gson gsonFactory() {
       return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ").create();
    }
    public String getJson(String url) {
        String fullUrl = webServiceUrl + url;
        HttpGet request = new HttpGet(fullUrl);
        StringBuffer result = new StringBuffer();
        String line = "";

        try {
            Log.d(TAG, "getJSON: " + fullUrl);
            HttpResponse response = client.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = rd.readLine()) != null)
                result.append(line);
        }
        catch (Exception ex) {
            Log.e(TAG, "could not get JSON: " + ex.getMessage());
        }

        Log.d(TAG, result.toString());

        return result.toString();
    }

    private HttpClient getClient() {
        return getClient("","");
    }

    private HttpClient getClient(String userName, String password) {
        DefaultHttpClient client = null;

        try {

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            // Setting up parameters
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "utf-8");
            params.setBooleanParameter("http.protocol.expect-continue", false);

            // Setting timeout
            HttpConnectionParams.setConnectionTimeout(params, 1000);
            HttpConnectionParams.setSoTimeout(params, 1000);

            // Registering schemes for both HTTP and HTTPS
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            // Creating thread safe client connection manager
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            // Creating HTTP client
            client = new DefaultHttpClient(ccm, params);

            //Registering user name and password for authentication

            if ("" != userName) {
                client.getCredentialsProvider().setCredentials(
                        new AuthScope(null, -1),
                        new UsernamePasswordCredentials(userName, password));
            }


        } catch (Exception e) {
            client = new DefaultHttpClient();
        }

        return client;
    }

    public boolean checkLogin(UserAccount account) {
        String fullUrl = webServiceUrl + "/auth/login";
        HttpGet request = new HttpGet(fullUrl);
        String line = "";
        StringBuffer result = new StringBuffer();

        int responseCode = 501;

        try {
            HttpResponse response = client.execute(request);
            responseCode = response.getStatusLine().getStatusCode();
        }
        catch (Exception ex) {
            Log.e("HTTPRequest", ex.toString());
        }

        return (responseCode == 200);
    }

    public void setHost(String host) {
        this.webServiceUrl = host;
    }
}



