package com.getbro.meetmeandroid.suggestion;

import android.view.View;
import android.widget.TextView;

import com.getbro.meetmeandroid.old.LabelLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 04/08/14.
 */
public class Suggestion {
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



    public static ArrayList<Suggestion> fromView(LabelLayout ll){
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

    public int getDrawableId(){
        return type.getDrawableId();
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
}


