package com.getbro.meetmeandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.getbro.meetmeandroid.generate.Account;
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

    public void resetApp() {
        Account acc = getAccount();
        if (acc != null) {
            session.destroyAccount(acc);
        }
    }

    public Account getAccount() {
        Account acc = session.queryAccounts().first();
        return acc;
    }

    public String getBasicAuth() {
        Account acc = getAccount();
        return String.format("%s:%s",acc.getNumber(),acc.getSecret());
    }
}
