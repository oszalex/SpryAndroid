package com.getbro.meetmeandroid.suggestion;

import android.graphics.drawable.Drawable;

import com.getbro.meetmeandroid.R;

import java.util.UnknownFormatConversionException;

public enum SuggestionTypes{
    PERSON(R.color.green),
    DATETIME(R.color.bittersweet_dark_red),
    PLACE(R.color.blue),
    TAG(R.color.purple);

    private final int colorRes;

    SuggestionTypes(int colorRes) {
        this.colorRes = colorRes;
    }

    public int getColorRes() {
        return colorRes;
    }
}
