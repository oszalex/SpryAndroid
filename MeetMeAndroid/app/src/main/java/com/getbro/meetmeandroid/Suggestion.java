package com.getbro.meetmeandroid;

/**
 * Created by chris on 04/08/14.
 */
public class Suggestion {
    private String value;
    private int type;

    public Suggestion(String value, int type){
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
