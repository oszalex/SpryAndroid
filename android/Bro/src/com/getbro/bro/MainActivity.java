package com.getbro.bro;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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

		/**
		 * TODO verify login
		 */

		/** Intent provides runtime bindings between components. */
		Intent intent = new Intent(this, InputActivity.class);

		/** Pass the username as an extra. To be displayed in the Input-Activity */
		EditText edit_username = (EditText) findViewById(R.id.edit_username);
		String username = edit_username.getText().toString();
		intent.putExtra(EXTRA_USERNAME, username);

		if (username.equals("wrong")) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Password");
			alertDialog.setMessage("Wrong");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Add your code for the button here.
				}
			});
			// Set the Icon for the Dialog
			// alertDialog.setIcon(R.drawable.icon);
			alertDialog.show();
		} else {

			// Start the new Input activity
			startActivity(intent);
		}
	}
}