package com.getbro.meetmeandroid.suggestion;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.getbro.meetmeandroid.util.TagListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 04/08/14.
 */
public class Suggestion implements Parcelable {
    private String value;
    private SuggestionTypes type;

    private Suggestion(){}

    public Suggestion(String value, SuggestionTypes type){
        this.value = value;
        this.type = type;
    }

    public static Suggestion fromView(TextView tv){
        //Suggestion suggestion = new Suggestion();


        //TextView text = (TextView) tv.findViewById(R.id.suggestion);

        //suggestion.setType(SuggestionTypes.getSuggestionTypefromDrawable(tv.getBackground()));
        //suggestion.setValue(text.getText().toString());

        return (Suggestion) tv.getTag();
    }



    public static ArrayList<Suggestion> fromView(TagListView ll){
        ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();

        for (int i = 0; i < ll.getChildCount(); ++i) {
            View v = ll.getChildAt(i);

            if (v instanceof TextView)
                suggestions.add((Suggestion) v.getTag());
        }

        return suggestions;
    }

    public static String listToString(List<Suggestion> suggestions){
        StringBuilder sb = new StringBuilder();

        for(Suggestion s: suggestions){
            sb.append(s.getValue());

            if(suggestions.indexOf(s) != suggestions.size() - 1) sb.append(" ");
        }

        return sb.toString();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SuggestionTypes getType() {
        return type;
    }

    public void setType(SuggestionTypes type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}


