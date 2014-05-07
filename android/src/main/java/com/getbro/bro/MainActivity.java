package com.getbro.bro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.getbro.bro.Data.Event;
import com.getbro.bro.Fragments.EventListFragment;
import com.getbro.bro.Fragments.UserListFragment;
import com.getbro.bro.Fragments.NewEventFragment;
import com.getbro.bro.Fragments.ProfilFragment;
import com.getbro.bro.Data.User;
import com.getbro.bro.Webservice.AsyncLoginResponse;
import com.getbro.bro.Webservice.DatabaseSync;
import com.getbro.bro.Webservice.HttpGetRequest;


public class MainActivity extends Activity implements AsyncLoginResponse {
    private final String TAG = MainActivity.class.getSimpleName();
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private HttpGetRequest httpRequest;

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

        //INFO
        Log.i(TAG, "DB has: " + User.listAll(User.class).size() + " and " + Event.listAll(Event.class).size() + " events");



        //configure webserver connection
        //FIX
        httpRequest = (HttpGetRequest)getApplication();
        httpRequest.setHost(getResources().getString(R.string.webService));
        httpRequest.configureClient(getResources().getString(R.string.webService),"chris","123");

        new DatabaseSync(this).execute();


        //if not logged in
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

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
                return new ProfilFragment(User.listAll(User.class).get(0));
            case 1:
                return new UserListFragment(User.listAll(User.class));
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

}
