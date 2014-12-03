package com.getbro.meetmeandroid.suggestion;

/**
 * Created by Alex on 03.12.2014.
 */
public class SuggestionLocation extends Suggestion {
    private String Location;

    public SuggestionLocation(String value, SuggestionTypes type, String location) {
        super(value, type);
        Location = location;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
