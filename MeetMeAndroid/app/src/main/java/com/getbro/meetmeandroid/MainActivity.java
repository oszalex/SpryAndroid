package com.getbro.meetmeandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
//import android.graphics.Outline;

public class MainActivity extends Activity {

    private final String URI = "http://api.getbro.com/v1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Button fab = (Button) findViewById(R.id.fabbutton);

        Outline mOutlineCircle;
        int shapeSize = getResources().getDimensionPixelSize(R.dimen.shape_size);
        mOutlineCircle = new Outline();
        mOutlineCircle.setRoundRect(0, 0, shapeSize, shapeSize, shapeSize / 2);

        fab.setOutline(mOutlineCircle);
        fab.setClipToOutline(true);
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


    /* auszüge aus dem Backendtest */

    public void allEvents(View v) {
        ListView lv = (ListView) findViewById(R.id.list);
        JSONArray jason = MeetMeAPI.getJSONFromServer(URI + "/events");
        eventToListView(lv,jason);
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
                        MainActivity.this, list,
                        R.layout.list_element, new String[]{ "creator","raw",
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
}
