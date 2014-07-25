package com.example.alex.backendtest;

import android.app.Activity;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Alex on 25.07.2014.
 */
public class comm {

    public static JSONObject JSONcreator(Activity main, String[] field)
    {
        EditText x;
        JSONObject jason = new JSONObject();
        try {
            for(int i=0;i<1;i++){
                //x = (EditText) findViewById(R.id.+ field );
                int resId = main.getResources().getIdentifier(field[i], "id", main.getPackageName());
                x = (EditText) main.findViewById(resId);
                jason.put(field[i], x.getText() );
            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        return jason;
    }
    //TODO: Geht auch anders zB als AsyncTask +  Refactore in extra Klasse
    public static void sendJason(final String URL, final JSONObject jason) {
        HttpResponse response;
        Thread t = new Thread() {
            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                Log.i("Sending",  jason.toString());
                try {
                    HttpPost post = new HttpPost(URL);
                    StringEntity se = new StringEntity( jason.toString());

                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");
                    post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if(response!=null){
                        InputStream in = response.getEntity().getContent();
                        String result = convertInputStreamToString(in);
                        Log.i("Response",result);
                    }
                    else
                    {
                        Log.e("Error","Response Null");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    Log.e("Error", e.toString());
                }
                Looper.loop(); //Loop in the message queue
            }
        };
        t.start();

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
    //TODO: Threading oder AsncTAsk sowie Refactoring
    public static JSONArray getJSONFromServer(String URL) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONArray jason = new JSONArray();
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                jason = new JSONArray(builder.toString());
            }
            else {
                Log.e("Error", "statuscode"+ statusCode );
            }
        } catch (ClientProtocolException e) {
            Log.e("Error", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error", e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("Error", e.toString());
            e.printStackTrace();
        }
        return jason;
    }
}
