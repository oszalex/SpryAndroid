package com.getbro.meetmeandroid;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
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
                creator_tv.setText(getContactName(context, String.valueOf(e.getCreatorID())));
            }
            if (raw_tv != null){
                raw_tv.setText(e.getRaw());
            }
            if (time_tv != null){
                time_tv.setText(e.getTime().toString());
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

}