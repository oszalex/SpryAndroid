package com.getbro.bro;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import com.getbro.bro.Webservice.HttpGetRequest;

public class MainActivity extends Activity {

	public final static String EXTRA_USERNAME = "com.getbro.bro.USERNAME";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the login button */
	public void login(View view) {


        Intent intent = new Intent(this,NewEventActivity.class);
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
}