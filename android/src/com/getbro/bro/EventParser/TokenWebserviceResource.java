package com.getbro.bro.EventParser;


import com.getbro.bro.EventParser.ITokenResource;
import com.getbro.bro.Json.Event;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TokenWebserviceResource implements ITokenResource {

    private String webServiceUrl;

    public TokenWebserviceResource(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    @Override
    public List<String> getPersonSuggestions(String token) {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getLabelSuggestions(String token) {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getLocationSuggestions(String token) {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getTimeSuggestion(String token) {
        return new ArrayList<String>();
    }
}
