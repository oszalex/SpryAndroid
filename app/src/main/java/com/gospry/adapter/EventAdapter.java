package com.gospry.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.gospry.MeetMeApp;
import com.gospry.R;
import com.gospry.generate.Event;
import com.gospry.generate.Keyword;
import com.gospry.util.C;

import java.util.Date;
import java.util.List;


public class EventAdapter extends CursorAdapter {
    final static String TAG = "EVENT";

    private MeetMeApp app;

    public EventAdapter(MeetMeApp app, Cursor c, boolean autoRequery) {
        super(app, c, autoRequery);
        this.app = app;
    }

    public static String getRelativeTimeSpan(Date time) {
        long current = System.currentTimeMillis();
        long time_milli = time.getTime();

        long min = 1000 * 60;
        long h = min * 60;
        long d = h * 24;
        long w = d * 7;

        long diff = Math.abs(current - time_milli);

        //greater than a week
        if (diff > w)
            return (diff / w) + "w";
        else if (diff > d)
            return (diff / d) + "d";
        else if (diff > h)
            return (diff / h) + "h";
        else if (diff > min)
            return (diff / min) + "m";

        return "just now";
    }

    //http://stackoverflow.com/questions/3079365/android-retrieve-contact-name-from-phone-number
    public String getContactName(String phoneNumber) {
        if (phoneNumber.equals(app.getAccount().getNumber())) return "ME";
        if (phoneNumber == null) return phoneNumber;
        if (phoneNumber.isEmpty()) return phoneNumber;

        ContentResolver cr = app.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (contactName == null) return phoneNumber;
        return contactName;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cell_event, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView creator = (TextView) view.findViewById(R.id.creator);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        TextView keywords = (TextView) view.findViewById(R.id.keywords);
        TextView state = (TextView) view.findViewById(R.id.state);

        Event event = Event.fromCursor(cursor);

        List<Keyword> keywordList = event.loadKeywords(app.getSession()).all();
        StringBuilder builder = new StringBuilder();
        for (Keyword keyword : keywordList) {
            builder.append(keyword.getText());
            builder.append(" ");
        }
        //keywords.setText(builder.toString());

        if (C.EVENT_STATE_ATTENDIN.equals(event.getInvitationstatus())) {
            state.setTextColor(Color.GREEN);
            state.setText("ATTENDING");
        } else if (C.EVENT_STATE_NOT_ATTENDING.equals(event.getInvitationstatus())) {
            state.setTextColor(Color.RED);
            state.setText("i'm out");
        } else if (C.EVENT_STATE_MAYBE.equals(event.getInvitationstatus())) {
            state.setTextColor(0xfffa5000);
            state.setText("MAYBE");
        } else {
            state.setText(null);
        }
        String creatorID = event.getUser();
        String creatorname = getContactName(creatorID);
        creator.setText("by " + creatorname);

        keywords.setText(event.getLocation());
        desc.setText(event.getDescription());
        time.setText(getRelativeTimeSpan(new Date(event.getStartTime())));
    }

    @Override
    public Object getItem(int position) {
        Event event = Event.fromCursor((Cursor) super.getItem(position));
        return event;
    }
}
