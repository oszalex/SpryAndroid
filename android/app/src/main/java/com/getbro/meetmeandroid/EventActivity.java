package com.getbro.meetmeandroid;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import android.support.v4.widget.SwipeRefreshLayout;


import com.getbro.meetmeandroid.adapter.EventAdapter;
import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.remote.RemoteCallback;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.state.EventsGetState;
import com.getbro.meetmeandroid.touch.SwipeDismissListViewTouchListener;
import com.getbro.meetmeandroid.util.C;

public class EventActivity extends ListActivity implements SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = EventActivity.class.toString();
    private EventAdapter adapter;
    private MeetMeApp app;
    private LocalSession session;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (MeetMeApp)getApplication();
        session = app.getSession();

        setTitle(null);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.refreshAnimimation1),
                getResources().getColor(R.color.refreshAnimimation2),
                getResources().getColor(R.color.refreshAnimimation3),
                getResources().getColor(R.color.refreshAnimimation4));

        swipeRefreshLayout.setOnRefreshListener(this);

        checkAuth();

        SwipeDismissListViewTouchListener listener = new SwipeDismissListViewTouchListener(getListView(), new SwipeDismissListViewTouchListener.SwipeCallback() {
            @Override
            public void swipe(boolean left, View view) {
            }

            @Override
            public void accept(int index) {
                Toast.makeText(EventActivity.this, "ACCEPT " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void decline(int index) {
                Toast.makeText(EventActivity.this, "DECLINE " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void detail(int index) {
                Toast.makeText(EventActivity.this, "DETAIL " + index, Toast.LENGTH_SHORT).show();

            }
        });
        listener.setmSwipeRefreshLayout(swipeRefreshLayout);
        getListView().setOnTouchListener(listener);
        getListView().setOnScrollListener(listener.makeScrollListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadAdapter();
    }

    private boolean checkAuth() {
        if (!app.getCtx().isAuthenticated()) {
            Intent it = new Intent(this, LoginActivity.class);
            startActivityForResult(it, C.REQ_LOGIN);
            return false;
        }
        return true;
    }

    private void reloadAdapter() {
        Cursor cursor = session.queryEvents().cursor();
        adapter = new EventAdapter(EventActivity.this, cursor, false);
        getListView().setAdapter(adapter);
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
    @Override
    public void onRefresh() {
        EventsGetState state = new EventsGetState(app.getCtx());
        state.setCallback(new RemoteCallback() {
            @Override
            public void onRequestOk(RemoteResponse response) {
                reloadAdapter();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onRequestFailed(RemoteResponse response) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        app.getCtx().invoke(state);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent it = new Intent(this, NewEventActivity.class);
            startActivity(it);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
