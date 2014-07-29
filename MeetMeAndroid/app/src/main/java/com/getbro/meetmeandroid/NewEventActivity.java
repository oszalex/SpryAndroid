package com.getbro.meetmeandroid;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.getbro.meetmeandroid.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class NewEventActivity extends Activity {
    private final String TAG = Activity.class.toString();

    private ArrayList<String> suggestions = new ArrayList<String>();
    private ArrayAdapter<String> autoCompleter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        getActionBar().setTitle(R.string.actionbar_newevent);

        suggestions.addAll(getContactNames(getApplicationContext()));

        autoCompleter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, suggestions);

        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.rawevent);
        textView.setAdapter(autoCompleter);
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


    /**
     * get all contact names with trailing '+' sign
     * @param ctx
     * @return
     */
    private static ArrayList<String> getContactNames(Context ctx){
        String contactName = null;
        ArrayList<String> contacts = new ArrayList<String>();
        Cursor cursor = ctx.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()){
            contactName  = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contacts.add("+" + contactName);
        }

        return contacts;
    }
}
