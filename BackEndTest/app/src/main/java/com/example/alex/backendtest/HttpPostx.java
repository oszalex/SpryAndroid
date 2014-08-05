package com.example.alex.backendtest;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
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
import java.util.Date;
import java.util.List;
import static org.apache.http.impl.cookie.DateUtils.formatDate;
import android.content.SharedPreferences;
import android.content.*;
public class HttpPostx extends AsyncTask<String, Void, HttpResponse>
{
   // public long userId = 436802118976L;
    public static Context mContext;
    SharedPreferences SP;
    DataDownloadListener dataDownloadListener;
    HttpPostx(Context context)
    {
        mContext = context;
    }
    @Override
    protected HttpResponse doInBackground(String... params) {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000); //Timeout Limit
        HttpResponse response = null;

        try {
            JSONObject jason= new JSONObject(params[1]);
            Log.i("Sending", jason.toString());
            HttpPost post = new HttpPost(params[0]);
            StringEntity se = new StringEntity(jason.toString());
            SP = mContext.getSharedPreferences("com.example.alex.backendtest", Context.MODE_PRIVATE);
            Date now = new Date();
            String signature = formatDate(now);
            String key = SP.getString("privateKey", "");
            Long userId = Long.parseLong(SP.getString("userId", ""));
            //Log.i("Keyzeu", key);
            String encsign = Keys.Encrypt(signature,Keys.loadPrivateKey(key));
           // Log.i("Keyzeug encsign", encsign);
            String basicAuth = "Basic " + new String(Base64.encode((Long.toString(userId)+":"+encsign).getBytes(), Base64.NO_WRAP));
            Log.i("BasicAuth", basicAuth);
            post.setHeader("Authorization", basicAuth);
            post.setHeader("Date", signature );
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
            Log.e("Error",e.toString());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(HttpResponse results)
    {
        if(results != null){
            Log.e("Result", results.toString());
            dataDownloadListener.dataDownloadedSuccessfully(results);
        }
        else
            Log.e("Result", "is null");
            dataDownloadListener.dataDownloadFailed();
       }
    public void setDataDownloadListener(DataDownloadListener dataDownloadListener) {
        Log.e("Listener", "is set");
        this.dataDownloadListener = dataDownloadListener;
    }
    public static interface DataDownloadListener {
        void dataDownloadedSuccessfully(Object data);
        void dataDownloadFailed();
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