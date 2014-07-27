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

                creator = "by " + getContactName(getApplicationContext(), creator);
                time = relativeTimeSpan(time);

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


    /* homebrew */

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }


    private static String relativeTimeSpan(String time){
        Date expires = new Date(Long.parseLong(time, 10));

        long now = System.currentTimeMillis();
        DateTime expiresDt = new DateTime(expires);
        DateTime nowDt = new DateTime();
        long difference = Math.abs(expires.getTime() - now);
        Period period = new Period(expiresDt, nowDt);
        PeriodFormatterBuilder formatterBuilder = new PeriodFormatterBuilder();
        if (difference > DateUtils.YEAR_IN_MILLIS) {
            formatterBuilder.appendYears().appendSuffix(" y");
        } else if (difference > DateUtils.DAY_IN_MILLIS * 30) {
            formatterBuilder.appendMonths().appendSuffix(" m");
        } else if (difference > DateUtils.WEEK_IN_MILLIS) {
            formatterBuilder.appendWeeks().appendSuffix(" w");
        } else if (difference > DateUtils.DAY_IN_MILLIS) {
            formatterBuilder.appendDays().appendSuffix(" d");
        } else if (difference > DateUtils.HOUR_IN_MILLIS) {
            formatterBuilder.appendHours().appendSuffix(" h");
        } else if (difference > DateUtils.MINUTE_IN_MILLIS) {
            formatterBuilder.appendMinutes().appendSuffix(" m");
        } else if (difference > DateUtils.SECOND_IN_MILLIS) {
            formatterBuilder.appendSeconds().appendSuffix(" s");
        }else{
            formatterBuilder.appendSeconds().appendSuffix("just now");
        }

        //return formatterBuilder.toFormatter().print(period);

        return "3w";

    }



}
