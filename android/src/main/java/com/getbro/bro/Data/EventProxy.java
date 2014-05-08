package com.getbro.bro.Data;

import android.os.AsyncTask;
import android.util.Log;

import com.getbro.bro.Webservice.HttpGetRequest;

import java.util.concurrent.ExecutionException;

public class EventProxy {
    private static final String TAG = EventProxy.class.getSimpleName();

    public static Event getUser(long id){
        Log.d(TAG, "try to fetch event: " + id);
        Event event = DatabaseManager.getInstance().getEvent(id);

        //try to find it on the internet
        if(event == null){
            Log.d(TAG, "use inet connection to fetch user");

            try {
                event = new AsyncTask<Long,Void, Event>(){

                    @Override
                    protected Event doInBackground(Long... ids) {
                        HttpGetRequest server = HttpGetRequest.getHttpGetRequest();
                        return server.getEvent(ids[0]);
                    }
                }.execute(id).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            if(event != null) DatabaseManager.getInstance().addEvent(event);
        }else {
            Log.d(TAG, "found user within database!");
        }

        return event;
    }
}
