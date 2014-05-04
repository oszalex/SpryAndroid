package com.getbro.bro.Webservice;

import android.app.Activity;
import android.content.Context;

/**
 * Created by chris on 04/05/14.
 */
public interface AsyncLoginResponse{
    public HttpGetRequest getHTTPRequest();
    void onLoginCheckFinish(Boolean output);
    public Context getApplicationContext ();
}
