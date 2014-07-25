package com.example.alex.backendtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import 	java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.creator))
                        .getText().toString();
                String cost = ((TextView) view.findViewById(R.id.raw))
                        .getText().toString();


                // Starting single contact activity
                /*Intent in = new Intent(getApplicationContext(),
                        SingleContactActivity.class);
                in.putExtra(TAG_NAME, name);
                in.putExtra(TAG_EMAIL, cost);
                in.putExtra(TAG_PHONE_MOBILE, description);
                startActivity(in);
*/
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    String URI = "http://84.114.33.167:8080";
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public JSONArray getJSONFromServer(String URL)
    {
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
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jason;
    }
    public void allEvents(View v) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ListView lv = (ListView) findViewById(R.id.listView);
        List<String> your_array_list = new ArrayList<String>();
        your_array_list.add("foo");
        your_array_list.add("bar");
        JSONArray jason = getJSONFromServer(URI+"/events");
        eventToListView(lv,jason);
                //Log.e(ParseJSON.class.toString(), "Failed to download file");
    }


    public void eventToListView(ListView lv, JSONArray jason)
    {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            for (int i = 0; i < jason.length(); i++) {
                JSONObject c = jason.getJSONObject(i);
            //    Toast.makeText(this, jason.toString(), Toast.LENGTH_SHORT).show();
                String creator = c.getString("creatorId");
                String raw = c.getString("raw");
                String time = c.getString("time");

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("creator", creator);
                map.put("raw", raw);
                map.put("time", time);
                list.add(map);
                ListAdapter adapter = new SimpleAdapter(
                        Main.this, list,
                        R.layout.event_element, new String[]{"creator", "raw",
                        "time"}, new int[]{R.id.creator,
                        R.id.raw, R.id.time}
                );
                lv.setAdapter(adapter);
            }
        }catch (JSONException e) {
                e.printStackTrace();
        }
    }
    public void userToListView(ListView lv, JSONArray jason)
    {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            for (int i = 0; i < jason.length(); i++) {
                JSONObject c = jason.getJSONObject(i);
                //    Toast.makeText(this, jason.toString(), Toast.LENGTH_SHORT).show();
                String name = c.getString("name");
                String id = c.getString("id");

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", name);
                map.put("id", id);
                list.add(map);
                ListAdapter adapter = new SimpleAdapter(
                        Main.this, list,
                        R.layout.user_element, new String[]{"name", "id"},
                        new int[]{R.id.name,
                        R.id.id}
                );
                lv.setAdapter(adapter);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String readJSON() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URI+"/events");
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
            } else {
               // Log.e(ParseJSON.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void viewEvent(View v) {

    }
    public void addUserToEvent(View v) {

    }
    public void createEvent(View v) {

    }
    public void viewUser(View v) {

    }
    public void allUsers(View v) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ListView lv = (ListView) findViewById(R.id.listView);
        List<String> your_array_list = new ArrayList<String>();
        your_array_list.add("foo");
        your_array_list.add("bar");
        JSONArray jason = getJSONFromServer(URI+"/users");
        userToListView(lv,jason);
        //Log.e(ParseJSON.class.toString(), "Failed to download file");
    }
    public void createUser(View v) {
        EditText x = (EditText) findViewById(R.id.editText3);

        JSONObject jason = new JSONObject();
        try {
            jason.put("name", x.getText() );
            jason.put("id", 24);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        sendJason(URI+"/users", jason);
    }
    public void sendJason(final String URL, final JSONObject jason) {
        //Toast.makeText(this, jason.toString(), Toast.LENGTH_SHORT).show();
        Thread t = new Thread() {
            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                try {
                    HttpPost post = new HttpPost(URL);
                    StringEntity se = new StringEntity( jason.toString());
                    Log.i("a",se.toString());
                    //se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setHeader("json",jason.toString());
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");
                    post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                        String result = convertInputStreamToString(in);
                        Log.i("a",result);
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                  //  createDialog("Error", "Cannot Establish Connection");
                }
                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();
    }
    // see http://androidsnippets.com/executing-a-http-get-request-with-httpclient
    public static InputStream getInputStreamFromUrl(String url) {
        InputStream content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            content = response.getEntity().getContent();
        } catch (Exception e) {
           // Log.("[GET REQUEST]", "Network exception", e);
        }
        return content;
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
