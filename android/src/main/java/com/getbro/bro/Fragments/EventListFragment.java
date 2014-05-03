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
import com.getbro.bro.R;
import com.getbro.bro.Webservice.HttpGetRequest;

/**
 * Created by chris on 03/05/14.
 */
public class EventListFragment extends Fragment {
    private HttpGetRequest httpRequest;

    public EventListFragment(HttpGetRequest httpRequest){
        this.httpRequest = httpRequest;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.activity_display_events, container, false);
        //ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.events_listview);

        new DownloadEventTask(this.getActivity()).execute();

        return v;
    }

    private class DownloadEventTask extends AsyncTask<Void,Void, Event[]> {
        Activity activity;

        DownloadEventTask(Activity activity) {
            this.activity = activity;
        }

        protected Event[] doInBackground(Void... params) {

            try {
                Event[] events = httpRequest.getAllEvents();
                return events;

            } catch (NullPointerException e){
                Log.w("MMM", "could not fetch events from server");
                return null;
            }
        }

        protected void onPostExecute(Event[] result) {
            EventsExpandableListAdapter eventsAdapter = new EventsExpandableListAdapter(activity,result);
            ExpandableListView listView = (ExpandableListView) activity.findViewById(R.id.events_listview);
            listView.setAdapter(eventsAdapter);
        }
    }
}