package com.getbro.bro.EventParser;



import java.util.List;


public interface ITokenResource {

    public List<String> getPersonSuggestions(String token);
    public List<String> getTagSuggestions(String token);
    public List<String> getLocationSuggestions(String token);
    public List<String> getTimeSuggestions(String token);
    public List<String> getEventSuggestions(String token);

}
