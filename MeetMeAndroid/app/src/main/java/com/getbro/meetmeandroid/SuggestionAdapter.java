package com.getbro.meetmeandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chris on 28/07/14.
 */


public class SuggestionAdapter extends ArrayAdapter<Suggestion> {
    final static String TAG = SuggestionAdapter.class.toString();

    // declaring our ArrayList of items
    private ArrayList<Suggestion> suggestions;
    private Context context;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public SuggestionAdapter(Context context, int textViewResourceId, ArrayList<Suggestion> suggestions) {
        super(context, textViewResourceId, suggestions);
        this.suggestions = suggestions;

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
            v = inflater.inflate(R.layout.suggestion_element, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Suggestion s = suggestions.get(position);

        if (s != null) {

            TextView tv = (TextView) v.findViewById(R.id.suggestion);
            tv.setBackgroundResource(s.getType());

            if (tv != null){
                tv.setText(s.getValue());
            }
        }

        // the view must be returned to our activity
        return v;

    }


}