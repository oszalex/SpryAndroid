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
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
//import android.graphics.Outline;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setTitle(R.string.actionbar_main);


        final ListView lv = (ListView) findViewById(R.id.list);
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

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


                APIEventAdapter m_adapter = new APIEventAdapter(MainActivity.this, R.layout.list_element, APIEvent.fromJSONEvent(result));
                lv.setAdapter(m_adapter);

                srl.setRefreshing(false);
                Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void createNewEvent(View v){
        Intent myIntent = new Intent(MainActivity.this, NewEventActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public boolean refresh(MenuItem v){
        allEvents();

        Toast.makeText(this,"refreshed", Toast.LENGTH_SHORT).show();
        return true;
    }

}
