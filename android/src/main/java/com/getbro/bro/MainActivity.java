package com.getbro.bro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import com.getbro.bro.EventParser.TokenWebserviceResource;
import com.getbro.bro.Webservice.HttpGetRequest;
import org.apache.http.client.methods.HttpGet;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

public class MainActivity extends Activity {

	public final static String EXTRA_USERNAME = "com.getbro.bro.USERNAME";
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        /** init sidebar **/
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the login button */
	public void login(View view) {
        EditText mUsername = (EditText)findViewById(R.id.login_username);
        EditText mPassword = (EditText)findViewById(R.id.login_password);

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        HttpGetRequest httpRequest = (HttpGetRequest)getApplication();
      //  httpRequest.configureClient(getResources().getString(R.string.webService),username,password);

        Intent intent = new Intent(this,ProfilActivity.class);
        startActivity(intent);

//		/**
//		 * TODO verify login and throw alert if no valid
//		 */
//
//		/** Intent provides runtime bindings between components. */
//		Intent intent = new Intent(this, InputActivity.class);
//
//		/** Pass the username as an extra. To be displayed in the Input-Activity */
//		EditText edit_username = (EditText) findViewById(R.id.edit_username);
//		String username = edit_username.getText().toString();
//		intent.putExtra(EXTRA_USERNAME, username);
//
//		startActivity(intent);
//
	}


    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }


    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        /*


         */
    }

    @Override
    public void setTitle(CharSequence title) {
        /*
        mTitle = title;
        getActionBar().setTitle(mTitle);
         */

    }
}
