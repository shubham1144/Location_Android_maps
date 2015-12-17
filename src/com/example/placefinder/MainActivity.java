package com.example.placefinder;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {

	public static final long MIN_TIME = 0;
	public static final float MIN_DISTANCE = 0;
	TextView textlat;
	TextView textlng;
	LatLng latlng;
	// creating a map variable to be used in the application to display map
	private GoogleMap map;
	//setting a zoom level for the camera
	private int zoomlevel = 13;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// mapping the class variables with the layout values
		textlat = (TextView) findViewById(R.id.latitude);
		textlng = (TextView) findViewById(R.id.longitude);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// create a location manager
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		System.out.println(LocationManager.GPS_PROVIDER);
		System.out.println(LocationManager.NETWORK_PROVIDER);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

		// use a fragment manager and access the map fragment from the layout
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		if (map != null) {
			map.getUiSettings().setCompassEnabled(true);
			map.setTrafficEnabled(true);
			map.setMyLocationEnabled(true);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {

		System.out.println("location change detected");
		double clat = location.getLatitude();
		double clng = location.getLongitude();
		// setting the changed latitude and longitude in the layout
		textlat.setText(Double.toString(clat));
		textlng.setText(Double.toString(clng));
		latlng = new LatLng(clat, clng);
		// once we find the location of the user set the marker to the current
		// location of the user
		// move the camera to the current location
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomlevel));
		// add marker to the current location
		map.addMarker(new MarkerOptions().position(latlng).title("IM HERE")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
