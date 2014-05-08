package com.getbro.bro.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data<T> {

    @Expose
    @SerializedName("data")
    public T data;


    public Data(T data) {
        this.data = data;
    }
}
