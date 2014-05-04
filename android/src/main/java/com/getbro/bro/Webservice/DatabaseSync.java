package com.getbro.bro.Webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.getbro.bro.Json.Event;
import com.getbro.bro.Json.User;
import com.getbro.bro.Model.UserModel;

/**
 * Created by chris on 04/05/14.
 */

public class DatabaseSync extends AsyncTask<Void, Void, Void> {
    private AsyncLoginResponse delegate = null;
    private HttpGetRequest httpRequest;

    public DatabaseSync(AsyncLoginResponse callback) {
        Log.d("Login", "new LoginCheck");

        this.delegate = callback;
        httpRequest = callback.getHTTPRequest();
    }


    @Override
    protected Void doInBackground(Void... params) {
        Log.d("Login", "some background work");

        //fetch events
        //events
        Event events[] = httpRequest.getAllEvents();

        //for(Event e: events){
        //    if (EventModel.findById(Book.class, 1);)
        //}

        //fetch own user object
        //users/me
        User me = httpRequest.getOwnUserElement();
        UserModel.updateOrInsert(delegate.getApplicationContext(), me);

        //fetch followers/followings


        return null;
    }

    protected void onPostExecute(Void result) {
        Log.d("Login", "Resultat ist da!");

        delegate.onLoginCheckFinish(true);
    }
}