package com.gospry;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gospry.remote.state.PostNewLocationState;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by rich on 21.11.14.
 */
public class CreateLocationTab1Fragment extends Fragment {


    EditText findlocation;
    Button searchbutton;
    ListView locationList;
    JsonArray result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_create_location_tab1, container, false);

        final ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);
        findlocation = (EditText) rootView.findViewById(R.id.findLocation);
        searchbutton = (Button) rootView.findViewById(R.id.search_button);
        locationList = (ListView) rootView.findViewById(R.id.listView);
        locationList.setAdapter(adapter);
        MeetMeApp app = (MeetMeApp) getActivity().getApplication();
        final AppCtx appCtx = app.getCtx();
        locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JsonObject location = null;
                try {
                    location = (JsonObject) result.get(position);
                } catch (Exception e) {

                }
                new PostNewLocationState(appCtx, location, findlocation.getText().toString()).start();
            }
        });
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO nicht immain thread?!
                AsyncHttpClient myClient = new AsyncHttpClient();

                myClient.get(
                        "http://nominatim.openstreetmap.org/search?format=json&q=" + findlocation.getText(), null,
                        new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                // If the response is JSONObject instead of expected JSONArray
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                                // Pull out the first event on the public timeline
                                try {
                                    JsonParser parser = new JsonParser();
                                    result = parser.parse(timeline.toString()).getAsJsonArray();
                                    JSONObject firstEvent = (JSONObject) timeline.get(0);
                                    Log.d("Nominatim:", firstEvent.toString());
                                    for (int i = 0; i < timeline.length(); i++) {
                                        adapter.add(timeline.get(i).toString());
                                    }

                                } catch (Exception e) {
                                    Log.d("Error:", e.toString());
                                }
                            }
                        });
            }
        });
        return rootView;
    }
}
