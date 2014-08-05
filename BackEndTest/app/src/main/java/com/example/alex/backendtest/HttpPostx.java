package com.example.alex.backendtest;


import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpPostx extends AsyncTask<String, Void, HttpResponse>
{

    @Override
    protected HttpResponse doInBackground(String... params) {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response = null;
        JSONObject jason;
        try {
            jason = new JSONObject(params[1]);
            Log.i("Sending", jason.toString());
            org.apache.http.client.methods.HttpPost post = new org.apache.http.client.methods.HttpPost(params[0]);
            String basicAuth = "Basic " + new String(Base64.encode("user:pass".getBytes(), Base64.NO_WRAP));
            post.setHeader("Authorization", basicAuth);
            StringEntity se = new StringEntity(jason.toString());

            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            post.setEntity(se);
            response = client.execute(post);

                    /*Checking response */
            if (response != null) {
                InputStream in = response.getEntity().getContent();
                String result = convertInputStreamToString(in);
                Log.i("Response", result);
            } else {
                Log.e("Error", "Response Null");
            }
            return response;
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
        return response;
    }
    protected void onPostExecute(String page)
    {
        //textView.setText(page);
      //  Toast toast = Toast.makeText(getApplicationContext(), page, Toast.LENGTH_SHORT);
      //  toast.show();
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }
}