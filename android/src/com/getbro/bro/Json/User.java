package com.getbro.bro.Json;

import com.google.gson.annotations.SerializedName;

public class User  extends Item {

    @SerializedName("sex")
    public String Sex;

    @SerializedName("username")
    public String UserName;

    @Override
    public String toString() {
        return UserName;
    }


}
