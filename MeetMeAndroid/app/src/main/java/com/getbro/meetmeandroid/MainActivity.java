package com.getbro.meetmeandroid;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import android.graphics.Outline;

public class MainActivity extends Activity {

    private final String URI = "http://api.getbro.com/v1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allEvents();

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

    public void allEvents() {
        ListView lv = (ListView) findViewById(R.id.list);
        JSONArray jason = MeetMeAPI.getJSONFromServer(URI + "/events");

        APIEventAdapter m_adapter = new APIEventAdapter(MainActivity.this, R.layout.list_element, MeetMeAPI.getEvents());
        lv.setAdapter(m_adapter);
    }

}
