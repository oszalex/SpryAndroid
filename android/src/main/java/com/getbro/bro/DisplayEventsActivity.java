package com.getbro.bro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.getbro.bro.EventParser.TokenWebserviceResource;
import com.getbro.bro.Json.Event;
import com.getbro.bro.Webservice.HttpGetRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

public class DisplayEventsActivity extends Activity {

//	private List<String> eventList = new ArrayList<String>();

	SparseArray<Event> events = new SparseArray<Event>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_events);


        HttpGetRequest httpRequest = (HttpGetRequest)getApplication();
        httpRequest.getAllEvents();

        //createData();
		
		ExpandableListView listView = (ExpandableListView) findViewById(R.id.events_listview);
		EventsExpandableListAdapter adapter = new EventsExpandableListAdapter(this, httpRequest.getAllEvents());
		listView.setAdapter(adapter);
		
		//new LoadEvents()
		//	.execute("http://api.getbro.com/events");
	}

//	public void createData() {
//		for (int j = 0; j < 5; j++) {
//			Event event = new Event("Event " + j);
//			event.children.add("@Dick Mack's Wien");
//			events.append(j, event);
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_events, menu);
		return true;

	}

	/**
	 * Handle interactions with the action-bar menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		/*
		 * switch (item.getItemId()) { case R.id.action_search: openSearch();
		 * return true; case R.id.action_compose: composeMessage(); return true;
		 * default: return super.onOptionsItemSelected(item); }
		 */
		return true;
	}

//	// TODO testirgendwas...please kill me
//	public void displayMessage() {
//		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//		alertDialog.setTitle("Title");
//		alertDialog.setMessage(eventList.toString());
//		alertDialog.show();
//	}

//	private static String getEventsFromServer(String url) {
//		InputStream inputStream = null;
//		String result = "";
//		try {
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
//			inputStream = httpResponse.getEntity().getContent();
//			if (inputStream != null)
//				result = convertInputStreamToString(inputStream);
//			else
//				result = "Did not work!";
//		} catch (Exception e) {
//			// TODO Was, wenn Fehler passiert?
//			return "fail";
//		}
//
//		return result;
//	}

//	private static String convertInputStreamToString(InputStream inputStream)
//			throws IOException {
//		BufferedReader bufferedReader = new BufferedReader(
//				new InputStreamReader(inputStream));
//		String line = "";
//		String result = "";
//		while ((line = bufferedReader.readLine()) != null)
//			result += line;
//
//		inputStream.close();
//		return result;
//
//	}

//	private class LoadEvents extends AsyncTask<String, Void, String> {
//
//		private final ProgressDialog dialog = new ProgressDialog(
//				DisplayEventsActivity.this);
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			dialog.setMessage("Please wait. Downloading events.");
//			dialog.show();
//		}
//
//		// onPostExecute displays the results of the AsyncTask.
//		@Override
//		protected void onPostExecute(String result) {
//
//			// Dismiss loading Dialog
//			dialog.dismiss();
//
//			JSONArray categories = null;
//
//			try {
//				categories = new JSONArray(result);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			if (categories == null) {
//				// TODO was tun wenn leer
//			}
//
//			// TODO was tun wenn categories leer
//			for (int i = 0; i < categories.length(); i++) {
//				try {
//					eventList
//							.add(categories.getJSONObject(i).getString("name"));
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//		}
//
//		@Override
//		protected String doInBackground(String... urls) {
//			return getEventsFromServer(urls[0]);
//		}
//	}
}
