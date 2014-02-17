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
import android.util.AndroidRuntimeException;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InputActivity extends Activity {

	private MapView myOpenMapView;
	private MapController myMapController;
	private MyLocationOverlay mMyLocationOverlay;

	private MyLocationOverlay myLocationOverlay;

	private Spinner catSpinner;
	private List<String> catList = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);

		myOpenMapView = (MapView) findViewById(R.id.map);
		myOpenMapView.setBuiltInZoomControls(true);
		myMapController = (MapController) myOpenMapView.getController();

		myLocationOverlay = new MyLocationOverlay(this, myOpenMapView);
		myLocationOverlay.enableMyLocation();
		myOpenMapView.getOverlays().add(myLocationOverlay);

		// TODO: last location

		myMapController.animateTo(new GeoPoint(482081743, 163738189));
		myMapController.setZoom(2);
		
		catSpinner = (Spinner) findViewById(R.id.input_categories);
		
		// call AsynTask to perform network operation on separate thread
        new LoadCategories().execute("http://bro.apiary.io/categories?offset=1&limit=3");
        
        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, catList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(dataAdapter);       
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
	
	private static String getCategories(String url){		
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
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!"; 
        } catch (Exception e) {
        	//TODO Was, wenn Fehler passiert?
            return "fail";
        }
 
        return result;
	}
	
	 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
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
			
			if(categories == null){
				//TODO was tun wenn leer
			}
			
			//TODO was tun wenn categories leer
			for(int i = 0; i < categories.length(); i++){
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
