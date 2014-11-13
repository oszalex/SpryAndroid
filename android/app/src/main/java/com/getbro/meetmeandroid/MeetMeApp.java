package com.getbro.meetmeandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.generate.RecordMigrator;

/**
 * rich
 * 11/1/14
 */
public class MeetMeApp extends Application {

    private AppCtx ctx;
    private SQLiteDatabase db;
    private LocalSession session;

    @Override
    public void onCreate() {
        super.onCreate();

        db = openOrCreateDatabase("name", MODE_PRIVATE, null);
        new RecordMigrator(db).migrate();
        session = new LocalSession(db);

        ctx = new AppCtx(this);
    }

    public @NonNull AppCtx getCtx() {
        return ctx;
    }

    public SharedPreferences getPrefs() {
        return getSharedPreferences(C.SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public LocalSession getSession() {
        return session;
    }
}
