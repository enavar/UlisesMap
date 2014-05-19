package org.escoladeltreball.ulisesmap.activities;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Settings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
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
		// create progress dialog
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
		editor.putBoolean("navigations", Settings.navigations);
		editor.putBoolean("hideLogo", Settings.hideLogo);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// for delete shared preferences, use for prevent some issue with
		// android emulator
		// need be commented for run in real phone
		// prefs.edit().clear().commit();
		// get settings stored on device
		Settings.routeType = prefs.getInt("routeType", R.id.walk);
		Settings.gps = prefs.getBoolean("gps", false);
		Settings.navigations = prefs.getBoolean("navigations", false);
		Settings.hideLogo = prefs.getBoolean("hideLogo", false);
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

		case R.id.navigations:
			Settings.navigations = !item.isCheckable();
			item.setChecked(Settings.navigations);
			Log.d("navigation", "" + Settings.navigations);
			return true;
		case R.id.car:
			return changeRouteStatus(item);
		case R.id.bicycle:
			return changeRouteStatus(item);
		case R.id.walk:
			return changeRouteStatus(item);
		case R.id.walk_transport:
			return changeRouteStatus(item);
		case R.id.myGPS:
			Settings.gps = !item.isChecked();
			item.setChecked(Settings.gps);
			return true;
		case R.id.menu_exit:
			moveTaskToBack(true);
			return true;
		case R.id.hideLogo:
			Settings.hideLogo = !item.isChecked();
			item.setChecked(Settings.hideLogo);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean changeRouteStatus(MenuItem item) {
		if (item.isChecked()) {
			item.setChecked(false);
		} else {
			item.setChecked(true);
			Settings.routeType = item.getItemId();
		}
		return true;
	}

	public void initMenu(Menu menu) {
		changeRouteStatus(menu.findItem(Settings.routeType));
		menu.findItem(R.id.myGPS).setChecked(Settings.gps);
		menu.findItem(R.id.navigations).setChecked(Settings.navigations);
		menu.findItem(R.id.menu_logo).setChecked(Settings.hideLogo);
	}
}