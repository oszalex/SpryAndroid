package com.gospry;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.gospry.R;
import com.gospry.adapter.EventAdapter;
import com.gospry.generate.Event;
import com.gospry.generate.LocalSession;
import com.gospry.remote.RemoteCallback;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.state.GetEventsState;
import com.gospry.remote.state.PostInvitationState;
import com.gospry.touch.SwipeDismissListViewTouchListener;
import com.gospry.util.C;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;

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
        app = (MeetMeApp) getApplication();
        session = app.getSession();

        setTitle(R.string.title_eventactivity);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.refreshAnimimation1),
                getResources().getColor(R.color.refreshAnimimation2),
                getResources().getColor(R.color.refreshAnimimation3),
                getResources().getColor(R.color.refreshAnimimation4));

        swipeRefreshLayout.setOnRefreshListener(this);

        checkAuth();
        /**
         * setup Test Button
         */
        FloatingActionButton testbutton = (FloatingActionButton) this.findViewById(R.id.testbutton);

        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EventActivity.this, TestActivity.class);
                startActivityForResult(it, C.REQ_NEW_EVENT);
            }
        });

        /**
         * setup FAB Button to add events
         */

        FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
        ListView listView = (ListView) findViewById(android.R.id.list);
        //listView.setOnTouchListener(new ShowHideOnScroll(fab));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EventActivity.this, NewEventActivity.class);
                startActivityForResult(it, C.REQ_NEW_EVENT);
            }
        });

        final AppCtx appCtx = app.getCtx();
        SwipeDismissListViewTouchListener listener = new SwipeDismissListViewTouchListener(getListView(), new SwipeDismissListViewTouchListener.SwipeCallback() {
            @Override
            public void accept(int index) {
                Event event = (Event) adapter.getItem(index);
                event.setAcceptState(C.EVENT_STATE_ATTENDIN);
                app.getSession().saveEvent(event);
                reloadAdapter();

                new PostInvitationState(appCtx, event).start();
            }

            @Override
            public void maybe(int index) {
                Event event = (Event) adapter.getItem(index);
                event.setAcceptState(C.EVENT_STATE_MAYBE);
                app.getSession().saveEvent(event);
                reloadAdapter();

                new PostInvitationState(appCtx, event).start();
            }

            @Override
            public void decline(int index) {
                Event event = (Event) adapter.getItem(index);
                event.setAcceptState(C.EVENT_STATE_NOT_ATTENDING);
                app.getSession().saveEvent(event);
                reloadAdapter();

                new PostInvitationState(appCtx, event).start();
            }

            @Override
            public void detail(int index) {
                Event event = (Event) adapter.getItem(index);
                Intent it = new Intent(EventActivity.this, EventDetailActivity.class);
                it.putExtra(C.EXTRA_EVENT_ID, event.getId());
                startActivity(it);
            }
        }, fab);
        listener.setmSwipeRefreshLayout(swipeRefreshLayout);
        getListView().setOnTouchListener(listener);
        getListView().setOnScrollListener(listener.makeScrollListener());

        reloadAdapter();

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
        //TODO: i think here somewhere change the order of the Events
        Cursor cursor = session.queryEvents().where("accept_state != 'NOT_ATTENDING'").orderBy("start_time desc").cursor();

        adapter = new EventAdapter(app, cursor, false);
        getListView().setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case C.REQ_LOGIN:
                checkAuth();
                break;
            case C.REQ_NEW_EVENT:
                if (resultCode == RESULT_OK) {
                    onRefresh();
                }
                break;
        }
    }

    /**
     * for refresh swipe refresh layout
     */
    @Override
    public void onRefresh() {
        GetEventsState state = new GetEventsState(app.getCtx());
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
        /*
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent it = new Intent(this, NewEventActivity.class);
            startActivityForResult(it, C.REQ_NEW_EVENT);
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }
}
