package com.gospry.suggestion;

import com.getbro.meetmeandroid.R;

public enum SuggestionTypes {
    PERSON(R.color.green),
    TIME(R.color.darkorange),
    DATE(R.color.bittersweet_dark_red),
    PLACE(R.color.blue),
    TAG(R.color.purple);

    private final int colorRes;

    SuggestionTypes(int colorRes) {
        this.colorRes = colorRes;
    }

    public static SuggestionTypes of(String text) {
        if (text.startsWith("#")) {
            return TAG;
        } else if (text.startsWith("%")) {
            return TIME;
        } else if (text.startsWith("@")) {
            return PLACE;
        }
        return PERSON;
    }

    public int getColorRes() {
        return colorRes;
    }
}
