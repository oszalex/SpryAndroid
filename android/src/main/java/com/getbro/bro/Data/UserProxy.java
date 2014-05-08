package com.getbro.bro.Data;

import android.os.AsyncTask;
import android.util.Log;

import com.getbro.bro.Webservice.HttpGetRequest;

import java.util.concurrent.ExecutionException;

public class UserProxy {
    private static final String TAG = UserProxy.class.getSimpleName();

    public static User getUser(long id){
        Log.d(TAG, "try to fetch user: " + id);

        User u = DatabaseManager.getInstance().getUser(id);

        //try to find it on the internet
        if(u == null){
            Log.d(TAG, "use inet connection to fetch user");

            try {
                u = new AsyncTask<Long,Void, User>(){

                    @Override
                    protected User doInBackground(Long... ids) {
                        HttpGetRequest server = HttpGetRequest.getHttpGetRequest();
                        return server.getUser(ids[0]);
                    }
                }.execute(id).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            if(u != null) DatabaseManager.getInstance().addUser(u);
        }else {
            Log.d(TAG, "found user within database!");
        }

        return u;
    }
}
