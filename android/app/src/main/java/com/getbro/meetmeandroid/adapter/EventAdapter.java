package com.getbro.meetmeandroid.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbro.meetmeandroid.R;
import com.getbro.meetmeandroid.generate.Event;
import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.old.APIEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chris on 28/07/14.
 */


public class EventAdapter extends CursorAdapter {
    final static String TAG = EventAdapter.class.toString();

    public EventAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cell_event, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView creator = (TextView) view.findViewById(R.id.creator);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView desc= (TextView) view.findViewById(R.id.desc);

        Event event = Event.fromCursor(cursor);

        creator.setText("by unkown");
        desc.setText(event.getDescription());
        time.setText(getRelativeTimeSpan(new Date(event.getStartTime())));
    }

    public static String getRelativeTimeSpan(Date time) {
        long current = System.currentTimeMillis();
        long time_milli = time.getTime();

        long min = 1000 * 60;
        long h = min * 60;
        long d = h * 24;
        long w = d * 7;

        long diff = Math.abs(current - time_milli);

        Log.i(TAG, "time difference: " + diff);

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
}