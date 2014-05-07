package com.getbro.bro.Webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.getbro.bro.Data.Event;
import com.getbro.bro.Data.User;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by chris on 04/05/14.
 */

public class DatabaseSync extends AsyncTask<Void, Void, Void> {
    private final String TAG = DatabaseSync.class.getSimpleName();
    private AsyncLoginResponse delegate = null;
    private HttpGetRequest httpRequest;

    public DatabaseSync(AsyncLoginResponse callback) {
        Log.d(TAG, "sync database");

        this.delegate = callback;
        httpRequest = callback.getHTTPRequest();

        assertNotNull("httpRequest cannot be null", httpRequest);
    }


    @Override
    protected Void doInBackground(Void... params) {
        //fetch events
        try {
            Event events[] = httpRequest.getAllEvents();

            for (Event e : events)
                Event.updateOrInsert(delegate.getApplicationContext(), e);

        } catch (NullPointerException e){
            Log.w(TAG, "cannot update event list! (NullPointer)");
        }

        //fetch own user object (users/me)
        try {
            User me = httpRequest.getOwnUserElement();
            User.updateOrInsert(delegate.getApplicationContext(), me);
        } catch(NullPointerException e){
            Log.w(TAG, "cannot update current user information! (NullPointer)");
        }

        //fetch followers/followings


        return null;
    }

    protected void onPostExecute(Void result) {
        Log.d(TAG, "Resultat ist da!");

        delegate.onLoginCheckFinish(true);
    }
}