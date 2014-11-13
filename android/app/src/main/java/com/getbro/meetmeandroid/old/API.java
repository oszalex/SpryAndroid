package com.getbro.meetmeandroid.old;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.List;

/**
 * Created by chris on 11/08/14.
 */
public class API {
    final static String basic_url = "http://spryv3.gattr.com";
    final static String TAG = API.class.toString();

    private static Builders.Any.B basicHelper(Context context, String url){
        //TODO add .basicAuthentication("", "")
        Builders.Any.B result = Ion.
                with(context).
                load(basic_url + url).
                basicAuthentication("4369911602033", "somepassword").
                setLogging(TAG, Log.VERBOSE).
                setHeader("Accept","application/json");
        Log.d(TAG, result.asJsonObject().toString());

        return result;
    }
    private static Builders.Any.B basicPOSTHelper(Context context, String url){
        //TODO add .basicAuthentication("", "")
        Builders.Any.B result = Ion.
                with(context).
                load("POST", basic_url + url).
                basicAuthentication("4369911602033", "somepassword").
                setHeader("Accept","application/json");
        Log.d(TAG, result.asJsonObject().toString());

        return result;
    }

    public static ResponseFuture<List<JSONEvent>> getEvents(Context context){
        return basicHelper(context,"/events").setLogging(TAG, Log.INFO).as(new TypeToken<List<JSONEvent>>() {
        });
    }

    public static ResponseFuture<JsonObject> createEvent(Context context, String raw){
        JsonObject raw_event = new JsonObject();

        raw_event.addProperty("raw", raw);

        return basicPOSTHelper(context, "/events").
                setHeader("Content-Type", "application/json").
                setLogging(TAG, Log.VERBOSE).
                setJsonObjectBody(raw_event).
                asJsonObject();
    }
}
