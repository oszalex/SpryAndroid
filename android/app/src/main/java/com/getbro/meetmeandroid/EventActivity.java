package com.getbro.meetmeandroid;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import android.support.v4.widget.SwipeRefreshLayout;


import com.getbro.meetmeandroid.adapter.EventAdapter;
import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.old.API;
import com.getbro.meetmeandroid.old.APIEvent;
import com.getbro.meetmeandroid.old.BanalAddEventActivity;
import com.getbro.meetmeandroid.old.JSONEvent;
import com.getbro.meetmeandroid.touch.SwipeDismissListViewTouchListener;
import com.getbro.meetmeandroid.util.C;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventActivity extends ListActivity implements SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = EventActivity.class.toString();
    private EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SwipeRefreshLayout srl = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        srl.setColorSchemeColors(
                getResources().getColor(R.color.refreshAnimimation1),
                getResources().getColor(R.color.refreshAnimimation2),
                getResources().getColor(R.color.refreshAnimimation3),
                getResources().getColor(R.color.refreshAnimimation4));

        srl.setOnRefreshListener(this);

        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (getListView() == null || getListView().getChildCount() == 0) ?
                                0 : getListView().getChildAt(0).getTop();
                srl.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        MeetMeApp app = (MeetMeApp)getApplication();
        LocalSession session = app.getSession();
        if (checkAuth()) {
            // authed -> reload
            Cursor cursor = session.queryEvents().cursor();
            adapter = new EventAdapter(EventActivity.this, cursor, false);
            getListView().setAdapter(adapter);
        }

        SwipeDismissListViewTouchListener listener = new SwipeDismissListViewTouchListener(getListView(), new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {

            }

            @Override
            public void onSwipeLeft(ListView listView, int[] reverseSortedPositions) {
                Toast.makeText(EventActivity.this, "DECLINE", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSwipeRight(ListView listView, int[] reverseSortedPositions) {
                Toast.makeText(EventActivity.this, "ACCEPT", Toast.LENGTH_LONG).show();

            }
        });
        getListView().setOnTouchListener(listener);
        getListView().setOnScrollListener(listener.makeScrollListener());
    }

    private boolean checkAuth() {
        MeetMeApp app = (MeetMeApp)getApplication();
        if (!app.getCtx().isAuthenticated()) {
            Intent it = new Intent(this, LoginActivity.class);
            startActivityForResult(it, C.REQ_LOGIN);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case C.REQ_LOGIN:
                checkAuth();
                break;
        }
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

    public void allEvents() {
        final ListView lv = (ListView) findViewById(android.R.id.list);
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        allEvents(lv, srl);
    }

    public void allEvents(final ListView lv,final SwipeRefreshLayout srl) {
    }
}
