package com.getbro.meetmeandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.getbro.meetmeandroid.R;

import org.json.JSONObject;

public class NewEventActivity extends Activity {
    private final String TAG = Activity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_event, menu);
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


    public void finish(View v){
        finish();
    }



    public void createEvent(View v){
        EditText rawEvent = (EditText) findViewById(R.id.rawevent);

        Log.v(TAG, "send event:" + rawEvent.getText().toString());

        MeetMeAPI.createEvent(rawEvent.getText().toString());
        finish();
    }
}
