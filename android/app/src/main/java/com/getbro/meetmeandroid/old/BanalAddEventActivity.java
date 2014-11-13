package com.getbro.meetmeandroid.old;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getbro.meetmeandroid.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;


public class BanalAddEventActivity extends Activity {
    static final String TAG = BanalAddEventActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banal_add_event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.banal_add_event, menu);
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


    public void createEvent(View v){
        TextView tv = (TextView) findViewById(R.id.rawPost);

        String rawtext = tv.getText().toString();


        Log.v(TAG, "send event:" + rawtext);

        //MeetMeAPI.createEvent(rawEvent.getText().toString());

        API.createEvent(this, rawtext).setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                finish();
            }
        });
    }
}
