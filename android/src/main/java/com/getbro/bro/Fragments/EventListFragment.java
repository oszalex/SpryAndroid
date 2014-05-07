package com.getbro.bro.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

import com.getbro.bro.EventsExpandableListAdapter;
import com.getbro.bro.Data.Event;
import com.getbro.bro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 03/05/14.
 */
public class EventListFragment extends Fragment {
    List<Event> events;

    public EventListFragment(List<Event> events){
        this.events = events;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.activity_display_events, container, false);
        //ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.events_listview);

        Activity activity = this.getActivity();

        Event[] result = events.toArray(new Event[events.size()]) ;
        EventsExpandableListAdapter eventsAdapter = new EventsExpandableListAdapter(activity,result);

        ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.events_listview);
        listView.setAdapter(eventsAdapter);




        return v;
    }

}