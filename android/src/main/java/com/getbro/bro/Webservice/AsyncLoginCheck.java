package com.getbro.bro.Webservice;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.getbro.bro.LoginActivity;
import com.getbro.bro.R;

/**
 * Created by chris on 04/05/14.
 */
public class AsyncLoginCheck extends AsyncTask<Void,Void, Boolean> {
    private String username;
    private String password;
    private AsyncLoginResponse delegate = null;
    private HttpGetRequest httpRequest;

    public AsyncLoginCheck(AsyncLoginResponse callback, String username, String password) {
        Log.d("Login", "new LoginCheck");

        this.username = username;
        this.password = password;
        this.delegate = callback;
        httpRequest = callback.getHTTPRequest();
    }


    private boolean checkCredentials(){
        httpRequest.configureClient(username, password);
        Log.d("Login", "Check credentials");

        return httpRequest.checkLogin();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d("Login", "some background work");
        return checkCredentials();
    }

    protected void onPostExecute(Boolean result) {
        Log.d("Login", "Resultat ist da!");

        delegate.onLoginCheckFinish(result);

    }
}