package com.getbro.bro.Webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.getbro.bro.Data.Event;
import com.getbro.bro.Data.User;

import static junit.framework.Assert.assertNotNull;

public class DatabaseSync extends AsyncTask<Void, Void, Void> {
    private final String TAG = DatabaseSync.class.getSimpleName();
    private HttpGetRequest httpRequest;

    public DatabaseSync() {
        Log.d(TAG, "sync database");

        httpRequest = HttpGetRequest.getHttpGetRequest();

        assertNotNull("httpRequest cannot be null", httpRequest);
    }


    @Override
    protected Void doInBackground(Void... params) {
        //fetch events
        Event events[] = {};
        try {
            events = httpRequest.getAllEvents();

            Log.d(TAG, "received following events: " + events);

            //for (Event e : events)
            //    Event.updateOrInsert(delegate.getApplicationContext(), e);
        } catch (NullPointerException e){
            Log.w(TAG, "cannot update event list! (NullPointer)");
        }


        //fetch own user object (users/me)
        try {
            User me = httpRequest.getOwnUserElement();

            Log.d(TAG, "users: " + me);
            //User.updateOrInsert(delegate.getApplicationContext(), me);
        } catch(NullPointerException e){
            Log.w(TAG, "cannot update current user information! (NullPointer)");
        }

        //fetch followers/followings


        return null;
    }

}