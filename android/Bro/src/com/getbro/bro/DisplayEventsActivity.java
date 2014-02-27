package com.getbro.bro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import android.view.Menu;
import android.view.View;

public class DisplayEventsActivity extends Activity {

	private List<String> catList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_events);

		new LoadEvents()
				.execute("http://api.getbro.com/events");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_events, menu);
		return true;

	}

	//TODO helper...deleteME
	public void displayMessage(View view) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Title");
		alertDialog.setMessage(catList.toString());
		alertDialog.show();
	}

	private static String getCategories(String url) {
		InputStream inputStream = null;
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			inputStream = httpResponse.getEntity().getContent();
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

	private class LoadEvents extends AsyncTask<String, Void, String> {

		private final ProgressDialog dialog = new ProgressDialog(
				DisplayEventsActivity.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Please wait. Downloading events.");
			dialog.show();
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			dialog.dismiss();
			
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
		
		@Override
		protected String doInBackground(String... urls) {
			return getCategories(urls[0]);
		}
	}
}
