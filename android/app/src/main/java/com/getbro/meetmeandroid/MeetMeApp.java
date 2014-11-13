package com.getbro.meetmeandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.getbro.meetmeandroid.generate.Event;
import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.generate.RecordMigrator;
import com.getbro.meetmeandroid.util.C;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;

import java.util.Date;

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

        if (session.queryEvents().count() < 20) {
            Event event = new Event();
            event.setDescription("a jausn beim wirtn");
            event.setMinAttending(0);
            event.setMaxAttending(4);
            event.setPrice(0);
            event.setStartTime(new Date(DateTime.now().withPeriodAdded(Days.FIVE,1).getMillis()));
            session.saveEvent(event);
        }

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
