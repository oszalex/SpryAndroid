package com.getbro.meetmeandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * rich
 * 11/1/14
 */
public class MeetMeApp extends Application {

    private AppCtx ctx;

    @Override
    public void onCreate() {
        super.onCreate();

        ctx = new AppCtx(this);
    }

    public @NonNull AppCtx getCtx() {
        return ctx;
    }

    public SharedPreferences getPrefs() {
        return getSharedPreferences(C.SHARED_PREFS, Context.MODE_PRIVATE);
    }
}
