package com.gospry.util;


import com.google.gson.JsonObject;

/**
 * Created by Alex on 12.12.2014.
 */
public class Response {
    private JsonObject response;

    public JsonObject getResponse() {
        return response;
    }

    public void setResponse(JsonObject response) {
        this.response = response;
    }
}
