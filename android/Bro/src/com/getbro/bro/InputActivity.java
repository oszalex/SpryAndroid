package com.getbro.bro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class InputActivity extends Activity {

	private MapView myOpenMapView;
	private MapController myMapController;
	private MyLocationOverlay mMyLocationOverlay;

	private Spinner catSpinner;
	private List<String> catList = new ArrayList<String>();

	private MyLocationOverlay myLocationOverlay = null;
	MyItemizedOverlay myItemizedOverlay = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);

		/************** Prepare Map **********************/
		final MapView mapView = (MapView) findViewById(R.id.map);
		mapView.setBuiltInZoomControls(true);

		Drawable marker = getResources().getDrawable(
				android.R.drawable.star_big_on);
		int markerWidth = marker.getIntrinsicWidth();
		int markerHeight = marker.getIntrinsicHeight();
		marker.setBounds(0, markerHeight, markerWidth, 0);

		ResourceProxy resourceProxy = new DefaultResourceProxyImpl(
				getApplicationContext());

		myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
		mapView.getOverlays().add(myItemizedOverlay);

		GeoPoint myPoint1 = new GeoPoint(0 * 1000000, 0 * 1000000);
		myItemizedOverlay.addItem(myPoint1, "myPoint1", "myPoint1");
		GeoPoint myPoint2 = new GeoPoint(50 * 1000000, 50 * 1000000);
		myItemizedOverlay.addItem(myPoint2, "myPoint2", "myPoint2");

		// Current Location
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);
		// myLocationOverlay.enableMyLocation();

		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(
						myLocationOverlay.getMyLocation());
			}
		});

		MapController myMapController = (MapController) mapView.getController();
		myMapController.setZoom(15);

		// call AsynTask to perform network operation on separate thread
		// Loads the categories from the server
		new LoadCategories()
				.execute("http://bro.apiary.io/categories?offset=1&limit=3");

		// Fill Autocomplete for categories
		//setSearchContext(catList);

	}

	private void setSearchContext(List<String> searchItems) {
		AutoCompleteTextView input_search = (AutoCompleteTextView) findViewById(R.id.input_search);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, searchItems);
		input_search.setAdapter(adapter);
	}

	// TODO Nur mal irgendwas...auslesen muss mans anders
	public void selectCategorie(View view){
		AutoCompleteTextView input_search = (AutoCompleteTextView) findViewById(R.id.input_search);
		input_search.setVisibility(View.VISIBLE);
		setSearchContext(catList);
		input_search.requestFocus();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
		myLocationOverlay.enableFollowLocation();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myLocationOverlay.disableMyLocation();
		myLocationOverlay.disableCompass();
		myLocationOverlay.disableFollowLocation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.input, menu);
		return true;
	}

	public void showFriend(View view) {
		/** Intent provides runtime bindings between components. */
		Intent intent = new Intent(this, ShowFriendActivity.class);
		startActivity(intent);
	}

	private static String getCategories(String url) {
		InputStream inputStream = null;
		String result = "";
		try {
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			// convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";
		} catch (Exception e) {
			// TODO Was, wenn Fehler passiert?
			return "fail";
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	public void showFriendlist(View view) {
		/** Intent provides runtime bindings between components. */
		Intent intent = new Intent(this, FriendlistActivity.class);
		startActivity(intent);

	}

	// TODO Nur mal irgendwas...auslesen muss mans anders
	public void selectDateAndTime(View view) {
		showTimePickerDialog();
		showDatePickerDialog();
	}

	public void showTimePickerDialog() {
		DialogFragment timePickerFragment = new TimePickerFragment();
		timePickerFragment.show(getFragmentManager(), "timePicker");
	}

	public void showDatePickerDialog() {
		DialogFragment datePickerFragment = new DatePickerFragment();
		datePickerFragment.show(getFragmentManager(), "datePicker");
	}

	private class LoadCategories extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return getCategories(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		// Is called automatically
		@Override
		protected void onPostExecute(String result) {

			JSONArray categories = null;

			try {
				categories = new JSONArray(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (categories == null) {
				// TODO was tun wenn leer
			}

			// TODO was tun wenn categories leer
			for (int i = 0; i < categories.length(); i++) {
				try {
					catList.add(categories.getJSONObject(i).getString("name"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
