package com.getbro.bro.Webservice;

import com.getbro.bro.Json.Event;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpGetRequest {

    private String webServiceUrl;

    public HttpGetRequest(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public void foo() {
        HttpClient client = new DefaultHttpClient();
        String fullUrl = webServiceUrl + "/events/1";
        HttpGet request = new HttpGet(fullUrl);
        request.addHeader("Authorization", "Bearer RsT5OjbzRn430zqMLgV3Ia");

        try {
            HttpResponse response = client.execute(request);
            int responsecode = response.getStatusLine().getStatusCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            Gson gson = new Gson();
            Event event = gson.fromJson(result.toString(), Event.class);
            String name = event.name;
        }
        catch (Exception ex) {
            java.lang.System.out.println(ex.getMessage());
        }

    }

}
