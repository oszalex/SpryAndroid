package com.getbro.bro.Webservice;

import com.getbro.bro.Json.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.security.KeyStore;


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

public class HttpGetRequest {

    private String webServiceUrl;

    public HttpGetRequest(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }


    public void foo() {


    }

    public void bar() {
        DefaultHttpClient client = null;
        String fullUrl = webServiceUrl + "/users/1";

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

            // Registering user name and password for authentication
//            client.getCredentialsProvider().setCredentials(
//                   new AuthScope(null, -1),
//                    new UsernamePasswordCredentials("raphi", "password"));

        } catch (Exception e) {
            client = new DefaultHttpClient();
        }

        try {
            HttpGet request = new HttpGet(fullUrl);
            //request.addHeader("Authorization", "Bearer RsT5OjbzRn430zqMLgV3Ia");

            HttpResponse response = client.execute(request);
            int responsecode = response.getStatusLine().getStatusCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }


            Gson gson = new Gson();
            Data<User> data = gson.fromJson(result.toString(), new TypeToken<Data<User>>(){}.getType());
            User user = data.data;
            String name = user.name;


            //br = new BufferedReader(new StringReader(" [ { \"id\": 1, \"sex\": \"male\", \"username\": \"chris\" }, { \"id\": 2, \"sex\": \"male\", \"username\": \"ommi\" }, { \"id\": 3, \"sex\": \"male\", \"username\": \"david\" } ]"));
            //User[] users = gson.fromJson(br, User[].class);
            //name=users[0].username;
        }
        catch (Exception ex) {
            java.lang.System.out.println(ex.getMessage());
        }

    }

}



