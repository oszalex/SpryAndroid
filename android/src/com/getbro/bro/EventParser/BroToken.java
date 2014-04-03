package com.getbro.bro.EventParser;

import java.lang.Exception;import java.lang.String;
import java.util.Arrays;import java.util.List;


public class BroToken {

    private final char[] tokenIdentifier = {'@', '#', '+', '!','_' };
    private ITokenResource tokenResource = null;

    public BroToken(ITokenResource tokenResource) {
        this.tokenResource = tokenResource;
    }

    public boolean isToken(String word) {
        if (word == null || word.length() == 0)
            return false;

        return Arrays.binarySearch(tokenIdentifier,word.charAt(0)) >= 0;
    }

    public List<String> getSuggestions(String token) throws Exception {
        if (token == null || token.length() == 0)
            return null;

        char identifier = token.charAt(0);
        token = token.substring(1);
        List<String> suggestions = null;


        switch  (identifier) {
            //case '@': suggestions =  getLocationSuggestions(token); break;
            case '_': suggestions = getLocationSuggestions(token); break; // just for testing, @ some doesn't work in emulator
            case '#': suggestions = getTagSuggestions(token); break;
            case '+': suggestions = getPersonSuggestions(token); break;
            case '!': suggestions = getTimeSuggestion(token); break;
            default: throw new Exception("Invalid token identifier: '" + identifier + "'");
        }

        return suggestions;
    }

    private List<String> getPersonSuggestions(String token) {
        return tokenResource.getPersonSuggestions(token);
    }

    private List<String> getTagSuggestions(String token) {
        return tokenResource.getTagSuggestions(token);
    }

    private List<String> getLocationSuggestions(String token) {
        return tokenResource.getLocationSuggestions(token);
    }

    private List<String> getTimeSuggestion(String token) {
        return tokenResource.getTimeSuggestions(token);
    }
}
