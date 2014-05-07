package com.getbro.bro.Data;

/**
 * Created by chris on 5/7/14.
 */
import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class DatabaseManager {
    private final String TAG = DatabaseManager.class.getSimpleName();

    static private DatabaseManager instance;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseHelper helper;
    private DatabaseManager(Context ctx) {
        Log.d(TAG, "Database helper set");
        helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return helper;
    }

    public List<Event> getAllEvents() {
        List<Event> events = null;
        try {
            events = getHelper().getEventDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            users = getHelper().getUserDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }



    public void addEvent(Event event) {
        try {
            getHelper().getEventDao().create(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEvent(Event event) {
        try {
            getHelper().getEventDao().update(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            Log.d(TAG, "getHelper() is null: " + (getHelper() == null));
            Log.d(TAG, "getUserDao() is null: " + (getHelper().getUserDao() == null));
            getHelper().getUserDao().create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        try {
            getHelper().getUserDao().update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}