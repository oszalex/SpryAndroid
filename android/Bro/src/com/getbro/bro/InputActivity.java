package com.getbro.bro;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;

public class InputActivity extends Activity {
	
	private MapView myOpenMapView;
	private MapController myMapController;
	private MyLocationOverlay mMyLocationOverlay;
	
	private MyLocationOverlay myLocationOverlay;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		
		myOpenMapView = (MapView)findViewById(R.id.map);
        //myOpenMapView.setBuiltInZoomControls(true);
        myMapController = (MapController) myOpenMapView.getController();
        
		
	    myLocationOverlay = new MyLocationOverlay(this, myOpenMapView);
        myLocationOverlay.enableMyLocation();
        myOpenMapView.getOverlays().add(myLocationOverlay);
        
        //TODO: last location
        
        myMapController.animateTo(new GeoPoint(482081743, 163738189));
        
        
        myMapController.setZoom(2);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.input, menu);
		return true;
	}

}
