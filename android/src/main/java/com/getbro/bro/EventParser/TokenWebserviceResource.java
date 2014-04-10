package com.getbro.bro.EventParser;


import com.getbro.bro.EventParser.ITokenResource;
import com.getbro.bro.Json.Event;
import com.getbro.bro.Json.User;
import com.getbro.bro.Webservice.HttpGetRequest;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenWebserviceResource implements ITokenResource {

    private HttpGetRequest httpRequest;

    public TokenWebserviceResource(HttpGetRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public List<String> getPersonSuggestions(String token) {
        User[] users = httpRequest.matchUser(token);
        ArrayList<String> userNames = new ArrayList<String>();

        for (User u : users)
            userNames.add("+" + u.UserName);
        return userNames;
    }

    @Override
    public List<String> getTagSuggestions(String token) {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getLocationSuggestions(String token) {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getTimeSuggestions(String token) {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getEventSuggestions(String token)  {
        Event[] events = httpRequest.getAllEvents();
        ArrayList<String> eventNames = new ArrayList<String>();

        for (Event e : events)
            eventNames.add(e.Name);
        return eventNames;
    }
}
