package com.getbro.meetmeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import android.support.v4.widget.SwipeRefreshLayout;


import com.getbro.meetmeandroid.API.API;
import com.getbro.meetmeandroid.API.APIEvent;
import com.getbro.meetmeandroid.API.JSONEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
//import android.graphics.Outline;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv = (ListView) findViewById(R.id.list);
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        srl.setColorSchemeColors(
                getResources().getColor(R.color.refreshAnimimation1),
                getResources().getColor(R.color.refreshAnimimation2),
                getResources().getColor(R.color.refreshAnimimation3),
                getResources().getColor(R.color.refreshAnimimation4));

        srl.setOnRefreshListener(this);


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (lv == null || lv.getChildCount() == 0) ?
                                0 : lv.getChildAt(0).getTop();
                srl.setEnabled(topRowVerticalPosition >= 0);
            }
        });


        allEvents(lv, srl);
    }

    /**
     * for refresh swipe refresh layout
     */
    @Override public void onRefresh() {
        allEvents();
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
        final ListView lv = (ListView) findViewById(R.id.list);
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        allEvents(lv, srl);
    }

    public void allEvents(final ListView lv,final SwipeRefreshLayout srl) {

        API.getEvents(this).setCallback(new FutureCallback<List<JSONEvent>>() {
            @Override
            public void onCompleted(Exception e, List<JSONEvent> result) {

                if(result == null){
                    Log.e(TAG, "shit. no events found!");
                    srl.setRefreshing(false);
                    return;
                }

                ArrayList<APIEvent> events = APIEvent.fromJSONEvent(result);

                Iterator<APIEvent> i = events.iterator();

                //filter all 'old' events from the past
                while (i.hasNext()){
                    APIEvent event = i.next();

                    //FIXME: event.getDuration() instead of fixed
                    Log.v(TAG, event.getTime().toString() + " ---");
                    if(event.getTime().getTime() + 120 * 60000 < System.currentTimeMillis()) {
                        Log.v(TAG, "remove element " + event.toString() + " because it's already old");
                        i.remove();
                    }

                    if(event.getTime().getTime() < System.currentTimeMillis()) {
                        Log.v(TAG, "event already starded " + event.toString());
                        event.setStared(true);
                    }
                }

                Log.i(TAG, "show " + events.size() + " events");


                APIEventAdapter m_adapter = new APIEventAdapter(MainActivity.this, R.layout.list_element, events);
                lv.setAdapter(m_adapter);

                srl.setRefreshing(false);
                Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void createNewEvent(View v){
        Intent myIntent = new Intent(MainActivity.this, BanalAddEventActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public boolean refresh(MenuItem v){
        allEvents();

        Toast.makeText(this,"refreshed", Toast.LENGTH_SHORT).show();
        return true;
    }

}
