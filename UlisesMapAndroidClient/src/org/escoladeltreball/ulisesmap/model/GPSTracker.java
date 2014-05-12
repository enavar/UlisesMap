package org.escoladeltreball.ulisesmap.model;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class GPSTracker implements LocationListener {

	private final Context mContext;
	private final MapView map;
	Marker nodeMarker;

	// flag for GPS status
	public boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

	// Declaring a Location Manager
	protected LocationManager locationManager;

	/* Constructor */

	public GPSTracker(Context context, MapView map) {
		this.mContext = context;
		this.map = map;
		initGPSTracker();
		if (canGetLocation) {
			initMarker();
			Log.d("latitude", String.valueOf(this.latitude));
			Log.d("longitud", String.valueOf(this.longitude));
		}
	}

	/* Getters and Setters */
	
	

	public double getLatitude() {
		return latitude;
	}

	public boolean isNetworkEnabled() {
		return isNetworkEnabled;
	}

	public void setNetworkEnabled(boolean isNetworkEnabled) {
		this.isNetworkEnabled = isNetworkEnabled;
	}

	public boolean isCanGetLocation() {
		return canGetLocation;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/* Interface methods */

	@Override
	public void onLocationChanged(Location location) {
		GeoPoint myLocation = new GeoPoint(location.getLatitude(),
				location.getLongitude());
		nodeMarker.setPosition(myLocation);
		IMapController mapController = map.getController();
		mapController.setCenter(myLocation);
		Log.d("latitude", String.valueOf(this.latitude));
		Log.d("longitud", String.valueOf(this.longitude));
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

	/* Own methods */

	public void initGPSTracker() {

		try {
			locationManager = (LocationManager) mContext
					.getSystemService(Context.LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			Log.d("isGPSEnabled", "=" + isGPSEnabled);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			Log.d("isNetworkEnabled", "=" + isNetworkEnabled);

			if (!(isGPSEnabled == false && isNetworkEnabled == false)) {

				this.canGetLocation = true;
				if (isNetworkEnabled) {
					location = null;
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					location = null;
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initMarker() {
		GeoPoint myLocation = new GeoPoint(this.latitude, this.longitude);
		nodeMarker = new Marker(map);
		nodeMarker.setPosition(myLocation);
		nodeMarker.setTitle("Longitud: " + this.longitude + "\nLatitud: "
				+ this.latitude);
		map.getOverlays().add(nodeMarker);
		IMapController mapController = map.getController();
		mapController.setCenter(myLocation);
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will
	 * lauch Settings Options
	 * */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Title
		alertDialog.setTitle("Network settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("Network is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

}
