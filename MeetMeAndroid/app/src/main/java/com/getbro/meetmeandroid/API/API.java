package com.getbro.meetmeandroid.API;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 11/08/14.
 */
public class API {
    final static String basic_url = "http://api.getbro.com/v1/";
    final static String TAG = API.class.toString();

    private static Builders.Any.B basicHelper(Context context, String url){
        //TODO add .basicAuthentication("", "")
        Builders.Any.B result = Ion.with(context).load(basic_url + url);
        Log.d(TAG, result.asJsonObject().toString());

        return result;
    }

    public static ResponseFuture<List<JSONEvent>> getEvents(Context context){
        return basicHelper(context,"events").setLogging(TAG, Log.VERBOSE).as(new TypeToken<List<JSONEvent>>() {
        });
    }
}
