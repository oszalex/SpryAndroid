package com.getbro.meetmeandroid.suggestion;

/**
 * Created by Alex on 03.12.2014.
 */
public class SuggestionTag extends Suggestion {
    private String tag;

    public SuggestionTag(String value, SuggestionTypes type, String tag) {
        super(value, type);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
