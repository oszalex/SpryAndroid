package com.getbro.meetmeandroid.suggestion;

import android.app.Application;
import android.os.Bundle;

import com.getbro.meetmeandroid.MeetMeApp;
import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.util.C;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rich on 21.11.14.
 */
public class SuggestionEngine {
    private static SuggestionEngine instance;

    public static SuggestionEngine getInstance() {
        if (instance == null) {
            instance = new SuggestionEngine();
        }
        return instance;
    }

    private Map<SuggestionTypes, List<Suggestion>> defaultSuggestions = new HashMap<>();

    public SuggestionEngine() {
        defaultSuggestions.put(SuggestionTypes.DATETIME,
                Arrays.asList(
                        new Suggestion("now",SuggestionTypes.DATETIME),
                        new Suggestion("30min",SuggestionTypes.DATETIME),
                        new Suggestion("1h",SuggestionTypes.DATETIME),
                        new Suggestion("2h",SuggestionTypes.DATETIME),
                        new Suggestion("afternoon",SuggestionTypes.DATETIME),
                        new Suggestion("monday",SuggestionTypes.DATETIME),
                        new Suggestion("saturday",SuggestionTypes.DATETIME),
                        new Suggestion("sunday",SuggestionTypes.DATETIME)
                ));
        defaultSuggestions.put(SuggestionTypes.PLACE,
                Arrays.asList(
                        new Suggestion("school",SuggestionTypes.PLACE),
                        new Suggestion("cafe",SuggestionTypes.PLACE),
                        new Suggestion("restaurant",SuggestionTypes.PLACE),
                        new Suggestion("park",SuggestionTypes.PLACE),
                        new Suggestion("nerd zone",SuggestionTypes.PLACE),
                        new Suggestion("university",SuggestionTypes.PLACE)
                ));
        defaultSuggestions.put(SuggestionTypes.PERSON,
                Arrays.asList(
                        new Suggestion("max",SuggestionTypes.PERSON),
                        new Suggestion("alex",SuggestionTypes.PERSON),
                        new Suggestion("rich",SuggestionTypes.PERSON),
                        new Suggestion("moritz",SuggestionTypes.PERSON)
                ));
        defaultSuggestions.put(SuggestionTypes.TAG,
                Arrays.asList(
                        new Suggestion("coffee",SuggestionTypes.TAG),
                        new Suggestion("banana",SuggestionTypes.TAG),
                        new Suggestion("cool stuff",SuggestionTypes.TAG),
                        new Suggestion("other",SuggestionTypes.TAG)
                ));
    }

    public List<Suggestion> provideSuggestions(MeetMeApp app, Bundle args) {
        if (args == null) {
            args = new Bundle();
        }
        //Suggestion lastAdded = args.getParcelable(C.EXTRA_LAST_ADDED);
        //Suggestion lastRemoved = args.getParcelable(C.EXTRA_LAST_REMOVED);

        // you might want to save suggestions into the database and load it
        // from there later
        // LocalSession session = app.getSession();

        List<Suggestion> suggestions = new ArrayList<>();

        for (List<Suggestion> suggestions1 : defaultSuggestions.values()) {
            suggestions.addAll(suggestions1);
        }

        Collections.shuffle(suggestions);

        return suggestions;
    }
}
