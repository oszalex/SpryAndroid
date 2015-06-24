package com.gospry;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;


/**
 * Created by rich on 21.11.14.
 */
public class CreateLocationActivity extends Activity {

    // Declaring our tabs and the corresponding fragments.
    ActionBar.Tab bmwTab, fordTab;
    Fragment createLocationTab1Fragment = new CreateLocationTab1Fragment();
    Fragment createLocationTab2Fragment = new CreateLocationTab2Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_location);
        createLocationTab1Fragment = new CreateLocationTab1Fragment();
        createLocationTab2Fragment = new CreateLocationTab2Fragment();
        // Asking for the default ActionBar element that our platform supports.
        ActionBar actionBar = getActionBar();

        // Screen handling while hiding ActionBar icon.
        actionBar.setDisplayShowHomeEnabled(false);

        // Screen handling while hiding Actionbar title.
        actionBar.setDisplayShowTitleEnabled(false);

        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        bmwTab = actionBar.newTab().setText("ABC");
        fordTab = actionBar.newTab().setText("DEF");
        // Setting tab listeners.
        bmwTab.setTabListener(new TabListener(createLocationTab1Fragment));
        fordTab.setTabListener(new TabListener(createLocationTab2Fragment));

        // Adding tabs to the ActionBar.
        actionBar.addTab(bmwTab);
        actionBar.addTab(fordTab);
    }
}
