package com.gospry.suggestion;

/**
 * Created by Alex on 03.12.2014.
 */
public class SuggestionTime extends Suggestion {


    //Uhrzeit des happenings in millisekunden des Tages
    private long starttime;

    public SuggestionTime(String value, SuggestionTypes type, long starttime) {
        super(value, type);
        this.starttime = starttime;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }
}

