package com.gospry.suggestion;

import android.os.Bundle;

import com.gospry.MeetMeApp;
import com.gospry.util.C;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by rich on 21.11.14.
 */
public class SuggestionEngine {
    private static SuggestionEngine instance;
    private Map<SuggestionTypes, List<Suggestion>> defaultSuggestions = new HashMap<>();
    //TODO: workaround...use a lib or sth
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public SuggestionEngine() {
        //TODO: other Date class?
        DateTime now = new DateTime();
        long current_time = now.getSecondOfDay() * 1000;
        long current_date = now.getMillis() - current_time;

        //TODO:Ohne List gehts nicht?!
        List<Suggestion> dates = new LinkedList<Suggestion>();
        dates.add(new SuggestionDate("today", SuggestionTypes.DATE, current_date));
        dates.add(new SuggestionDate("tomorrow", SuggestionTypes.DATE, current_date + 86400000));
        dates.add(new SuggestionDate("in 2 days", SuggestionTypes.DATE, current_date + 172800000));
        for (int i = 0; i < 6; i++) {
            //TODO: fix right days
            String desc = days[i];
            long timestamp;
            dates.add(new SuggestionDate(desc, SuggestionTypes.DATE, current_date + 172800000));
        }
        defaultSuggestions.put(SuggestionTypes.DATE, dates);

        //TODO: Uhrzeiten automatisch hinzufügen je nach aktueller Uhrzeit und Datum
        List<Suggestion> times = new LinkedList<Suggestion>();
        times.add(new SuggestionTime("now", SuggestionTypes.TIME, current_time));
        times.add(new SuggestionTime("in 30min", SuggestionTypes.TIME, current_time + 1800000));
        times.add(new SuggestionTime("in 1h", SuggestionTypes.TIME, current_time + 3600000));
        times.add(new SuggestionTime("in 2h", SuggestionTypes.TIME, current_time + 7200000));
        defaultSuggestions.put(SuggestionTypes.TIME, times);

        //TODO: Was wird angezeigt? was wird intern verwendet?
        List<Suggestion> locations = new LinkedList<Suggestion>();
        locations.add(new SuggestionLocation("school", SuggestionTypes.PLACE, "Schule?"));
        locations.add(new SuggestionLocation("Volksgarten", SuggestionTypes.PLACE, "Volksgarten"));
        locations.add(new SuggestionLocation("Westbahnhof", SuggestionTypes.PLACE, "U3 Westbahnhof"));
        defaultSuggestions.put(SuggestionTypes.PLACE, locations);

        List<Suggestion> friends = new LinkedList<Suggestion>();
        friends.add(new SuggestionContact("Adi", SuggestionTypes.PERSON, 436802118978L));
        friends.add(new SuggestionContact("Chris", SuggestionTypes.PERSON, 436991234567L));
        friends.add(new SuggestionContact("Benni", SuggestionTypes.PERSON, 4368123445L));
        friends.add(new SuggestionContact("Rich", SuggestionTypes.PERSON, 43664123456789L));
        //Automatische Kontakte
        friends.addAll(MeetMeApp.getfavcontacts());
        //25 TOP Kontakte laden TODO: check ob genug da sind? Auftelung im View geht besser
        //TODO: Gruppen hinzufügen und nach erstellen fragen ob Gruppe erstellt werden soll

        friends = friends.subList(0, Math.min(25, friends.size()));
        defaultSuggestions.put(SuggestionTypes.PERSON, friends);

        List<Suggestion> tags = new LinkedList<Suggestion>();
        tags.add(new SuggestionTag("sport", SuggestionTypes.TAG, "sport"));
        tags.add(new SuggestionTag("chill", SuggestionTypes.TAG, "chill"));
        tags.add(new SuggestionTag("eat", SuggestionTypes.TAG, "eat"));
        tags.add(new SuggestionTag("other", SuggestionTypes.TAG, "other"));
        defaultSuggestions.put(SuggestionTypes.TAG, tags);
    }

    public static SuggestionEngine getInstance() {
        if (instance == null) {
            instance = new SuggestionEngine();
        }
        return instance;
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

        /*for (List<Suggestion> suggestions1 : defaultSuggestions.values()) {
            suggestions.addAll(suggestions1);
        }
      */
        suggestions.addAll(defaultSuggestions.get(getType(args.getInt(C.SUGGESTIONTYPE))));
        Collections.shuffle(suggestions);
        return suggestions;
    }

    private SuggestionTypes getType(int type) {
        switch (type) {
            case C.NEWEVENT_DATE_RANK:
                return SuggestionTypes.DATE;
            case C.NEWEVENT_TIME_RANK:
                return SuggestionTypes.TIME;
            case C.NEWEVENT_LOCATION_RANK:
                return SuggestionTypes.PLACE;
            case C.NEWEVENT_TAG_RANK:
                return SuggestionTypes.TAG;
            case C.NEWEVENT_FRIENDS_RANK:
                return SuggestionTypes.PERSON;
            default:
                return SuggestionTypes.PERSON;
        }
    }

}
