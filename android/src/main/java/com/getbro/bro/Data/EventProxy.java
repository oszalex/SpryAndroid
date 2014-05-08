package com.getbro.bro.Data;

import android.util.Log;

import com.getbro.bro.Webservice.AsyncLoginResponse;
import com.getbro.bro.Webservice.HttpGetRequest;

/**
 * Created by chris on 5/7/14.
 */

public class EventProxy {
    private static final String TAG = EventProxy.class.getSimpleName();

    public static Event getUser(long id){
        Log.d(TAG, "try to fetch event: " + id);
        Event e = DatabaseManager.getInstance().getEvent(id);

        //try to find it on the internet
        if(e == null){
            Log.d(TAG, "use inet connection to fetch event");

            HttpGetRequest server = HttpGetRequest.getHttpGetRequest();
            e = server.getEvent(id);

            if(e != null) DatabaseManager.getInstance().addEvent(e);
        }

        return e;
    }
}
