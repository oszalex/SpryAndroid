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

    public String URI = "http://84.114.33.167:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.listView);
     /*   lv.setOnItemClickListener(new OnItemClickListener() {

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

            }
        });
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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


    public void allEvents(View v) {
        ListView lv = (ListView) findViewById(R.id.listView);
        JSONArray jason = comm.getJSONFromServer(URI+"/events");
        eventToListView(lv,jason);
    }
    public void viewEvent(View v) {

    }
    public void addUserToEvent(View v) {

    }
    public void createEvent(View v) {
        JSONObject x = comm.JSONcreator(this, new String[] {"raw"});
        comm.sendJason(URI+"/events",x );
        Toast.makeText(this, "Event "+ x.toString() +" created", Toast.LENGTH_SHORT).show();
    }
    public void viewUser(View v) {

    }
    public void allUsers(View v) {
        ListView lv = (ListView) findViewById(R.id.listView);
        JSONArray jason = comm.getJSONFromServer(URI+"/users");
        userToListView(lv,jason);
    }
    public void createUser(View v) {
        JSONObject x = comm.JSONcreator(this, new String[] {"name"});
        comm.sendJason(URI+"/users",x );
        Toast.makeText(this, "User "+ x.toString() +" created", Toast.LENGTH_SHORT).show();
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
                        R.layout.event_element, new String[]{ "creator","raw",
                        "time"
                        }, new int[]{
                        R.id.creator,R.id.raw,R.id.time}
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


}
