package com.getbro.bro.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.getbro.bro.EventsExpandableListAdapter;
import com.getbro.bro.Json.Event;
import com.getbro.bro.Model.EventModel;
import com.getbro.bro.R;
import com.getbro.bro.Webservice.HttpGetRequest;

import java.util.List;

/**
 * Created by chris on 03/05/14.
 */
public class EventListFragment extends Fragment {

    public EventListFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.activity_display_events, container, false);
        //ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.events_listview);

        Activity activity = this.getActivity();

        List<EventModel> events = EventModel.listAll(EventModel.class);
        EventModel[] result = events.toArray(new EventModel[events.size()]) ;
        EventsExpandableListAdapter eventsAdapter = new EventsExpandableListAdapter(activity,result);


        ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.events_listview);
        listView.setAdapter(eventsAdapter);




        return v;
    }

}