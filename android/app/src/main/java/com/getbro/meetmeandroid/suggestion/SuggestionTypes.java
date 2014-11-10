package com.getbro.meetmeandroid.suggestion;

import android.graphics.drawable.Drawable;

import com.getbro.meetmeandroid.R;

import java.util.UnknownFormatConversionException;

public enum SuggestionTypes{
    PERSON, DATETIME, TAG;


    public static SuggestionTypes getSuggestionTypefromDrawable(Drawable drawable){
        return SuggestionTypes.DATETIME;
    }

    public int getDrawableId(){
        switch (this){
            case PERSON:
                return R.drawable.name_label;
            case DATETIME:
                return R.drawable.time_label;
        }

        return R.drawable.tag_label;
    }
}
