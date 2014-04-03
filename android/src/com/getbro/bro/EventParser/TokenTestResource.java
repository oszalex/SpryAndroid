package com.getbro.bro.EventParser;

import com.getbro.bro.EventParser.ITokenResource;

import java.lang.Override;import java.lang.String;import java.util.ArrayList;
import java.util.List;

public class TokenTestResource implements ITokenResource {

    List<String> people = new ArrayList<String>();
    List<String> locations = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    List<String> labels = new ArrayList<String>();

    public TokenTestResource() {
        people.add("+Raphi");
        people.add("+Peter");
        people.add("+Christoph");
        people.add("+Test");

        locations.add("@Wolkersdorf_Im_Weinviertel");
        locations.add("@Baden");
        locations.add("@Wien");
        locations.add("@Wiener_Neustadt");
    }
    ArrayList<String> arraylist = new ArrayList<String>();

    private List<String> search(String token) {

        List<String> options = null;

        switch (token.charAt(0)) {
            case '@': options = locations; break;
            case '+': options = people; break;
            default: options = new ArrayList<String>();
        }

        ArrayList<String> results = new ArrayList<String>();

        for (String s : options) {
            if (s.contains(token))
                results.add(s);
        }

        return results;
    }

    @Override
    public List<String> getEventSuggestions(String token) {
       return search(token);
    }

    @Override
    public List<String> getPersonSuggestions(String token) {
        return search(token);
    }

    @Override
    public List<String> getTagSuggestions(String token) {
        return search(token);
    }

    @Override
    public List<String> getLocationSuggestions(String token) {
        return search(token);
    }

    @Override
    public List<String> getTimeSuggestions(String token) {
        return search(token);
    }
}
