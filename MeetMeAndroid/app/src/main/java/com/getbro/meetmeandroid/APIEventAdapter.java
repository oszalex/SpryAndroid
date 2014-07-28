package com.getbro.meetmeandroid;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.getbro.meetmeandroid.APIObjects.APIEvent;

/**
 * Created by chris on 28/07/14.
 */


public class APIEventAdapter extends ArrayAdapter<APIEvent> {

    // declaring our ArrayList of items
    private ArrayList<APIEvent> objects;
    private Context context;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public APIEventAdapter(Context context, int textViewResourceId, ArrayList<APIEvent> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;

        this.context = context;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_element, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        APIEvent e = objects.get(position);

        if (e != null) {

            TextView creator_tv = (TextView) v.findViewById(R.id.creator);
            TextView raw_tv = (TextView) v.findViewById(R.id.raw);
            TextView time_tv = (TextView) v.findViewById(R.id.time);


            if (creator_tv != null){
                creator_tv.setText(Html.fromHtml("<b>by </b> " + getContactName(context, String.valueOf(e.getCreatorID()) )));
            }
            if (raw_tv != null){
                raw_tv.setText(colorTags(context, e.getRaw()));
            }
            if (time_tv != null){
                time_tv.setText(getRelativeTimeSpan(e.getTime()));
            }

        }

        // the view must be returned to our activity
        return v;

    }


    /* homebrew */

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }


    public static String getRelativeTimeSpan(Date time){
        long current = System.currentTimeMillis();
        long time_milli = time.getTime();

        double min = 1000*60.0;
        double h = min*60;
        double d = h * 24;
        double w = d*7;

        long diff = time_milli - current;

        //greater than a week
        if(diff > w)
            return (diff/w) + "w";
        else if(diff > d)
            return (diff/d) + "d";
        else if(diff > h)
            return (diff/h) + "h";
        else if(diff > min)
            return (diff/min) + "m";

        return "just now";
    }



    public static SpannableStringBuilder colorTags(Context cxt, String text){
        final Pattern hashtag = Pattern.compile("#\\w+");
        final Pattern at = Pattern.compile("(\\s|\\A)@(\\w+)");
        final Pattern name = Pattern.compile("(\\s|\\A)\\+(\\w+)");

        final SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        final ForegroundColorSpan at_color = new ForegroundColorSpan(cxt.getResources().getColor(R.color.orange));
        final ForegroundColorSpan hash_color = new ForegroundColorSpan(cxt.getResources().getColor(R.color.light_blue));
        final ForegroundColorSpan name_color = new ForegroundColorSpan(cxt.getResources().getColor(R.color.green));

        final Matcher matcher = hashtag.matcher(text);
        final Matcher at_matcher = at.matcher(text);
        final Matcher name_matcher = name.matcher(text);


        while (matcher.find()) {
            spannable.setSpan(
                    hash_color, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        while (at_matcher.find()) {
            spannable.setSpan(
                    at_color, at_matcher.start(), at_matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        while (name_matcher.find()) {
            spannable.setSpan(
                    name_color, name_matcher.start(), name_matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        return spannable;
    }


}