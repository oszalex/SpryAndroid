package com.gospry.old;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gospry.R;
import com.gospry.view.HorizontalItemSelector;


public class NewEventIdeaActivity extends Activity {

    private SelectorViewHolder whereViewHolder;
    private SelectorViewHolder whomViewHolder;
    private SelectorViewHolder whenViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea_event);
    }

    @Override
    protected void onResume() {
        super.onResume();

        whereViewHolder = new SelectorViewHolder(findViewById(R.id.where));
        whomViewHolder = new SelectorViewHolder(findViewById(R.id.whom));
        whenViewHolder = new SelectorViewHolder(findViewById(R.id.when));

        whereViewHolder.addAll("my place", "your place", "school", "university", "that bar");
        whomViewHolder.addAll("Max Mustermann", "Richard", "That Guy", "Chrisiii", "Alex");
        whenViewHolder.addAll("now!", "30min", "1h", "afternoon", "after lunch", "tomorrow");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class SelectorViewHolder {

        HorizontalItemSelector itemSelector;

        public SelectorViewHolder(View view) {
            itemSelector = (HorizontalItemSelector) view.findViewById(R.id.item_selector);
        }

        public void addAll(String... args) {
            itemSelector.addAll(args);
        }
    }
}
