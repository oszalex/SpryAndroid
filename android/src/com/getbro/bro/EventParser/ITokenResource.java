package com.getbro.bro.EventParser;

import java.util.List;


public interface ITokenResource {

    public List<String> getPersonSuggestions(String token);
    public List<String> getLabelSuggestions(String token);
    public List<String> getLocationSuggestions(String token);
    public List<String> getTimeSuggestion(String token);
}
