package com.getbro.meetmeandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.getbro.meetmeandroid.generate.Account;
import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.generate.RecordMigrator;
import com.getbro.meetmeandroid.suggestion.Suggestion;
import com.getbro.meetmeandroid.suggestion.SuggestionContact;
import com.getbro.meetmeandroid.suggestion.SuggestionTypes;
import com.getbro.meetmeandroid.util.C;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * rich
 * 11/1/14
 */
public class MeetMeApp extends Application {

    private static Locale locale;
    private static List<Suggestion> favcontacts;
    private AppCtx ctx;
    private SQLiteDatabase db;
    private LocalSession session;

    public static List<Suggestion> getfavcontacts() {
        return favcontacts;
    }

    public static Locale getLocale() {
        return locale;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db = openOrCreateDatabase("name", MODE_PRIVATE, null);
        new RecordMigrator(db).migrate();
        session = new LocalSession(db);
        //TODO: Locale static? Locale Problem with Austria(is only DE isntead of de_at), therefore national numbers cannot be converted
        String locale = getResources().getConfiguration().locale.getCountry();
        String[] test = Locale.getISOCountries();
        ctx = new AppCtx(this);
        //TODO: beim start der app  laden und hin und wieder refreshen
        //TODO:workaround momentan, in zukunft eigene kontaktedatenbank
        favcontacts = getContacts();
    }

    public
    @NonNull
    AppCtx getCtx() {
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
        return String.format("%s:%s", acc.getNumber(), acc.getSecret());
    }

    public List<Suggestion> getContacts() {
        ArrayList<Suggestion> listContacts = new ArrayList<Suggestion>();
        // Tag_Contact neu = new Tag_Contact("alex","06802118976");

        Cursor contacts = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.TIMES_CONTACTED);
        contacts.moveToLast();
        int i = 0;

        int nameFieldColumnIndex = contacts.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
        int numberFieldColumnIndex = contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        while (contacts.moveToPrevious()) {
            Suggestion contact = new SuggestionContact(contacts.getString(nameFieldColumnIndex), SuggestionTypes.PERSON, contacts.getString(numberFieldColumnIndex));
            listContacts.add(contact);
        }
        contacts.close();
        return listContacts;
    }
}
