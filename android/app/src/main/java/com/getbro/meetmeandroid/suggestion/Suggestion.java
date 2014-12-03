package com.getbro.meetmeandroid.suggestion;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chris on 04/08/14.
 */
public class Suggestion implements Parcelable {
    public static final Creator<Suggestion> CREATOR = new Creator<Suggestion>() {
        @Override
        public Suggestion createFromParcel(Parcel source) {
            return new Suggestion(source);
        }

        @Override
        public Suggestion[] newArray(int size) {
            return new Suggestion[size];
        }
    };
    private String value;
    private SuggestionTypes type;

    public Suggestion(String value, SuggestionTypes type) {
        this.value = value;
        this.type = type;
    }

    private Suggestion(Parcel in) {
        value = in.readString();
        type = (SuggestionTypes) in.readSerializable();
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
        dest.writeString(value);
        dest.writeSerializable(type);
    }

}


