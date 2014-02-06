package com.getbro.bro;

import java.util.Calendar;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;

public class InputActivity extends Activity {
	
	private MapView myOpenMapView;
	private MapController myMapController;
	private MyLocationOverlay mMyLocationOverlay;
	
	private MyLocationOverlay myLocationOverlay;
	
	
	/**
	 * Date and Time
	 */
	private Calendar calendar;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		
		myOpenMapView = (MapView)findViewById(R.id.map);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = (MapController) myOpenMapView.getController();
        
		
	    myLocationOverlay = new MyLocationOverlay(this, myOpenMapView);
        myLocationOverlay.enableMyLocation();
        myOpenMapView.getOverlays().add(myLocationOverlay);
        
        //TODO: last location
        
        myMapController.animateTo(new GeoPoint(482081743, 163738189));       
        myMapController.setZoom(2);
        
        
        /**
         * Set Date and Time to current Date and Time
         */
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.input, menu);
		return true;
	}
	
	
	//TODO Nur mal irgendwas...auslesen muss mans anders
	public void selectDateAndTime(View view){
		showTimePickerDialog();
		showDatePickerDialog();
	}
	
	public void showTimePickerDialog(){		
		DialogFragment timePickerFragment = new TimePickerFragment();
		timePickerFragment.show(getFragmentManager(), "timePicker");
	}
	
	public void showDatePickerDialog(){
		DialogFragment datePickerFragment = new DatePickerFragment();
		datePickerFragment.show(getFragmentManager(), "datePicker");
	}
	
}
