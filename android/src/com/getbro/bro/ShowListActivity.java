package com.getbro.bro;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ShowListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_list);
		
		Bundle extras = getIntent().getExtras();
		
		setTitle(extras.getString("title"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_list, menu);
		return true;
	}

}
