package org.escoladeltreball.ulisesmap;

import org.escoladeltreball.ulisesmap.model.Settings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends Activity {

	protected SharedPreferences prefs;
	public ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences("ulises", Context.MODE_PRIVATE);
		//create progress dialog
		progress = new ProgressDialog(this);
		progress.setMessage("Loading data...");
		progress.setIndeterminate(false);	
	}


	@Override
	protected void onPause() {
		super.onPause();
		// store setting on device
		Editor editor = prefs.edit();
		editor.putInt("routeType", Settings.routeType);
		editor.putBoolean("gps", Settings.gps);
		editor.putBoolean("gps", Settings.navigations);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// for delete shared preferences
		// prefs.edit().clear().commit();
		// get settings stored on device
		Settings.routeType = prefs.getInt("routeType", R.id.walk);
		Settings.gps = prefs.getBoolean("gps", false);
		Settings.navigations = prefs.getBoolean("navigations", false);
		// Initiating Menu XML file (menu.xml)
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		initMenu(menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		initMenu(menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
		case R.id.menu_navigations:
			if (item.isChecked()) {
				item.setChecked(false);
				Settings.navigations = false;
			} else {
				item.setChecked(true);
				Settings.navigations = true;
			}
			return true;
		case R.id.car:
			changeRouteStatus(item);
			return true;
		case R.id.bicycle:
			changeRouteStatus(item);
			return true;
		case R.id.walk:
			changeRouteStatus(item);
			return true;
		case R.id.myGPS:
			if (item.isChecked()) {
				item.setChecked(false);
				Settings.gps = false;
			} else {
				item.setChecked(true);
				Settings.gps = true;
			}
			return true;
		case R.id.menu_exit:
			moveTaskToBack(true);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void changeRouteStatus(MenuItem item) {
		if (item.isChecked()) {
			item.setChecked(false);
		} else {
			item.setChecked(true);
			Settings.routeType = item.getItemId();
		}		
	}

	public void initMenu(Menu menu) {
		changeRouteStatus(menu.findItem(Settings.routeType));
		if (Settings.gps) menu.findItem(R.id.myGPS).setChecked(true);
		else menu.findItem(R.id.myGPS).setChecked(false);
		if (Settings.navigations) menu.findItem(R.id.navigations).setChecked(true);
		else menu.findItem(R.id.navigations).setChecked(false);
		
	}
}