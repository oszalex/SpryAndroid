package com.getbro.bro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.support.v4.widget.DrawerLayout;
import android.widget.ExpandableListView;
import android.widget.ListView;
import com.getbro.bro.Fragments.UserListFragment;
import com.getbro.bro.Fragments.NewEventFragment;
import com.getbro.bro.Fragments.ProfilFragment;
import com.getbro.bro.Json.Event;
import com.getbro.bro.Json.User;
import com.getbro.bro.Webservice.AsyncLoginResponse;
import com.getbro.bro.Webservice.DatabaseSync;
import com.getbro.bro.Webservice.HttpGetRequest;

import java.util.ArrayList;


public class MainActivity extends Activity implements AsyncLoginResponse {
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private HttpGetRequest httpRequest;
    private ArrayList<User> users;
    private User me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = this.getTitle();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /** init sidebar **/
        mPlanetTitles = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setIcon(R.drawable.ic_drawer);
        getActionBar().setHomeButtonEnabled(true);
        selectItem(2);


        //TEST LOGIN



        //configure webserver connection
        //FIX
        httpRequest = (HttpGetRequest)getApplication();
        httpRequest.setHost(getResources().getString(R.string.webService));
        httpRequest.configureClient(getResources().getString(R.string.webService),"chris","123");

        new DatabaseSync(this).execute();


        //if not logged in
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        //new DownloadUserProfile(this).execute();
        //new DownloadFriendsTask(this).execute();

	}


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public HttpGetRequest getHTTPRequest() {
        return httpRequest;
    }

    @Override
    public void onLoginCheckFinish(Boolean output) {

    }


    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }


    private void selectItem(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(
                R.id.content_frame,
                getFragment(position)
        ).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private Fragment getFragment(int i){
        switch(i){
            case 0:
                return new ProfilFragment(me);
            case 1:
                return new UserListFragment(users);
            case 2:
                return new EventListFragment();
            case 3:
                return new NewEventFragment(httpRequest);
            default:
                return new EventListFragment();
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }


    public class EventListFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View v = inflater.inflate(R.layout.activity_display_events, container, false);
            //ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.events_listview);

            new DownloadEventTask(this.getActivity()).execute();

            return v;
        }

        private class DownloadEventTask extends AsyncTask<Void,Void, Event[]> {
            Activity activity;

            DownloadEventTask(Activity activity) {
                this.activity = activity;
            }

            protected Event[] doInBackground(Void... params) {

                try {
                    Event[] events = httpRequest.getAllEvents();
                    return events;

                } catch (NullPointerException e){
                    Log.w("MMM", "could not fetch events from server");
                    return null;
                }
            }

            protected void onPostExecute(Event[] result) {
                EventsExpandableListAdapter eventsAdapter = new EventsExpandableListAdapter(activity,result);
                ExpandableListView listView = (ExpandableListView) activity.findViewById(R.id.events_listview);
                listView.setAdapter(eventsAdapter);
            }
        }
    }


/*
    private class DownloadFriendsTask extends AsyncTask<Void,Void, String[]> {
        Activity activity;

        DownloadFriendsTask(Activity activity) {
            this.activity = activity;
        }

        protected String[] doInBackground(Void... params) {
            String[] users = null;

            try {
                users = httpRequest.getFriends();
                return users;

            } catch (NullPointerException e){
                Log.w("MMM", "could not fetch events from server");
            }

            return users;
        }

        protected void onPostExecute(String[] result) {

            MainActivity.this.users = new ArrayList<User>();

            for(int i=0; i < result.length; i++){
                MainActivity.this.users.add(new User("male",result[i], null));
            }
        }
    }


    private class DownloadUserProfile extends AsyncTask<Void,Void, User> {
        Activity activity;

        DownloadUserProfile(Activity activity) {
            this.activity = activity;
        }

        protected User doInBackground(Void... params) {
            User me = null;

            try {
                me = httpRequest.getOwnUserElement();
                return me;

            } catch (NullPointerException e){
                Log.w("MMM", "could not fetch userprofile from server");
            }

            return me;
        }

        protected void onPostExecute(User me) {
            MainActivity.this.me = me;
        }
    }

*/
}
