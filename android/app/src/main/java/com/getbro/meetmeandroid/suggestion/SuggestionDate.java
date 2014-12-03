package com.getbro.meetmeandroid.suggestion;

/**
 * Created by Alex on 03.12.2014.
 */
public class SuggestionDate extends Suggestion {
    //Datum des happenings in millisekunden seit 1970
    private long startdate;

    public SuggestionDate(String value, SuggestionTypes type, long startdate) {
        super(value, type);
        this.startdate = startdate;
    }

    public long getStartdate() {
        return startdate;
    }

    public void setStartdate(long startdate) {
        this.startdate = startdate;
    }
}
