package com.getbro.bro.Data;

import android.util.Log;

import com.getbro.bro.Data.User;
import com.getbro.bro.Webservice.AsyncLoginResponse;
import com.getbro.bro.Webservice.HttpGetRequest;

/**
 * Created by chris on 04/05/14.
 */
public class UserProxy {
    private static final String TAG = UserProxy.class.getSimpleName();

    public static User getUser(long id){
        Log.d(TAG, "try to fetch user: " + id);

        User u = DatabaseManager.getInstance().getUser(id);

        //try to find it on the internet
        if(u == null){
            Log.d(TAG, "use inet connection to fetch user");

            HttpGetRequest server = HttpGetRequest.getHttpGetRequest();
            u = server.getUser(id);

            if(u != null) DatabaseManager.getInstance().addUser(u);
        }

        return u;
    }
}
