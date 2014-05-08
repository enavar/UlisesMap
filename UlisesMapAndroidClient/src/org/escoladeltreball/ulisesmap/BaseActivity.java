package org.escoladeltreball.ulisesmap;

import org.escoladeltreball.ulisesmap.model.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends Activity {

	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	protected void onPause() {
		super.onPause();
		// store setting on device
		Editor editor = prefs.edit();
		editor.putInt("routeType", Settings.routeType);
		editor.putBoolean("gps", Settings.gps);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		prefs = getSharedPreferences("ulises", Context.MODE_PRIVATE);
		// for delete shared preferences
		// prefs.edit().clear().commit();
		// get settings stored on device
		Settings.routeType = prefs.getInt("routeType", R.id.walk);
		Settings.gps = prefs.getBoolean("gps", true);
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

		case R.id.menu_search:
			Toast.makeText(this, "Search is Selected", Toast.LENGTH_SHORT)
					.show();
			return true;

		case R.id.car:
			changeRouteStatus(item);
		case R.id.bicycle:
			changeRouteStatus(item);
		case R.id.walk:
			changeRouteStatus(item);
		case R.id.myGPS:
			if (item.isChecked()) {
				item.setChecked(false);
				Settings.gps = false;
			} else {
				item.setChecked(true);
				Settings.gps = true;
			}
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
		if (Settings.gps) {
			menu.findItem(R.id.myGPS).setChecked(true);
		} else {
			menu.findItem(R.id.myGPS).setChecked(false);
		}
	}
}