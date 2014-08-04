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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewEventActivity extends Activity {
    private final String TAG = Activity.class.toString();

    private ArrayList<Suggestion> s = new ArrayList<Suggestion>();
    private SuggestionAdapter m_adapter;

    private int state = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        getActionBar().setTitle(R.string.actionbar_newevent);

        setupSuggest();
    }



    private ArrayList<Suggestion> updateSuggestions(){
        //TODO: optimize
        s.removeAll(s);

        if(state == 0) {
            s.add(new Suggestion("tomorrow", R.drawable.time_label));
            s.add(new Suggestion("now", R.drawable.time_label));
            s.add(new Suggestion("monday", R.drawable.time_label));
            s.add(new Suggestion("tuesday", R.drawable.time_label));
            s.add(new Suggestion("after work", R.drawable.time_label));
            s.add(new Suggestion("today", R.drawable.time_label));
            s.add(new Suggestion("never", R.drawable.time_label));
        } else if(state == 1) {
            s.addAll(getContactSuggestions(getApplicationContext()));
        } else if (state == 2){
            s.add(new Suggestion("#sometag", R.drawable.tag_label));
            s.add(new Suggestion("#hash", R.drawable.tag_label));
            s.add(new Suggestion("#nice", R.drawable.tag_label));
        }

        m_adapter.notifyDataSetChanged();

        state++;

        return s;
    }

    private void setupSuggest(){
        int resource;
        final GridView grid = (GridView) findViewById(R.id.suggestionGrid);
        final EditText text = (EditText) findViewById(R.id.rawevent);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            Object o = grid.getItemAtPosition(position);

            TextView label = (TextView)arg1.findViewById(R.id.suggestion);

            String suggestion = label.getText().toString();

            //remove suggestion from list
            //label.setVisibility(View.GONE);

            //add suggestion to text
            text.setText(text.getText() + " " + suggestion);

            //set cursor to the end
            text.setSelection(text.getText().length());

            updateSuggestions();
            }
        });

        m_adapter = new SuggestionAdapter(NewEventActivity.this, R.layout.suggestion_element, s);
        updateSuggestions();
        grid.setAdapter(m_adapter);
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
    private static ArrayList<Suggestion> getContactSuggestions(Context ctx){
        String contactName = null;
        ArrayList<Suggestion> contacts = new ArrayList<Suggestion>();
        Cursor cursor = ctx.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()){
            contactName  = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contacts.add(new Suggestion("+" + contactName, R.drawable.name_label));
        }

        return contacts;
    }
}
