package com.gospry.util;

/**
 * rich
 * 11/1/14
 */
public class C {
    public static final int REQ_LOGIN = 0x100;
    public static final int REQ_NEW_EVENT = 0x101;

    public static final String SHARED_PREFS = "general_app_prefs";
    public static final String PREF_LOGGED_IN = "logged_in";
    public static final String EXTRA_LAST_ADDED = "last_added";
    public static final String EXTRA_LAST_REMOVED = "last_rem";
    public static final String EXTRA_EVENT_ID = "event_id";

    public static final String EVENT_STATE_ATTENDIN = "ATTENDING";
    public static final String EVENT_STATE_MAYBE = "MAYBE";
    public static final String EVENT_STATE_NOT_ATTENDING = "NOT_ATTENDING";

    public static final int NEWEVENT_DATE_RANK = 1;
    public static final int NEWEVENT_TIME_RANK = 2;
    public static final int NEWEVENT_LOCATION_RANK = 3;
    public static final int NEWEVENT_TAG_RANK = 4;
    public static final int NEWEVENT_FRIENDS_RANK = 5;

    public static final String SUGGESTIONTYPE = "suggestiontype";

    public static final String SERVER_VERSION = "v4";
    public static final String SERVER_ADDRESS = "e543f2a2-inkrement.node.tutum.io";
    // public static final String SERVER_ADDRESS = "192.168.0.13";
    // public static final String SERVER_VERSION = "";
    public static final int SERVER_PORT = 443;


}
