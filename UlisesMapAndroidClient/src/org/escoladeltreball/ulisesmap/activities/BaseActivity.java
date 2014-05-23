/**
 * Copyright (c) 2014, Oleksander Dovbysh & Elisabet Navarro & Sheila Perez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.escoladeltreball.ulisesmap.activities;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Settings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * BaseActivity Handle a option menu used in almost all activities which allow
 * to a user personalize a view of route on the map. Load a user preferences
 * from an android SharedPreferences when an activity is created and store it in
 * Setting class which is used while user stay in this activity and store them
 * back when user start a new one. Also keep a references to a ProgressBar shown
 * while a new activity is loading.
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class BaseActivity extends Activity {

	/** for store user preferences */
	protected SharedPreferences prefs;
	/** references to a message and simple animation */
	public ProgressDialog progress;
	private final static String PREF_NAME = "ulises";

	/**
	 * IntentLaucher Class that launches a background activity.
	 * 
	 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
	 * @version: 1.0
	 */
	protected class IntentLauncher extends AsyncTask<Intent, Void, String> {

		@Override
		/**
		 * Check for internet connection before start  new activity
		 */
		protected void onPreExecute() {
			super.onPreExecute();
			if (!isNetworkConnected()) {
				showToast();
				progress.dismiss();
				this.cancel(true);
			}
		}

		/**
		 * Launch Activity
		 */
		@Override
		protected String doInBackground(Intent... intent) {
			startActivity(intent[0]);
			return null;
		}

		/**
		 * Show progress bar
		 */
		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
		}
	}

	@Override
	/**
	 * Load an application preferences from android database and
	 * create a simple animation for show while doing some background task
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		// create progress dialog
		progress = new ProgressDialog(this);
		progress.setMessage(getString(R.string.loadMessage));
		progress.setIndeterminate(false);
		progress.setCancelable(true);
	}

	@Override
	/**
	 * While an activity is no longer at foreground store a user preferences to android database
	 */
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
	/**
	 * Load an application options from android database to specific class which handle
	 * its while staying in activity
	 */
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
	/**
	 * Initiate menu each time user open it
	 */
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
			Settings.navigations = !item.isChecked();
			item.setChecked(Settings.navigations);
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

	/**
	 * Change a route status to a new one
	 * 
	 * @param item
	 *            a chosen menu item
	 * @return a true after change item status
	 */
	public boolean changeRouteStatus(MenuItem item) {
		if (item.isChecked()) {
			item.setChecked(false);
		} else {
			item.setChecked(true);
			Settings.routeType = item.getItemId();
		}
		return true;
	}

	/**
	 * Load to a menu all values
	 * 
	 * @param menu
	 *            a menu to be shown
	 */
	public void initMenu(Menu menu) {
		changeRouteStatus(menu.findItem(Settings.routeType));
		menu.findItem(R.id.myGPS).setChecked(Settings.gps);
		menu.findItem(R.id.navigations).setChecked(Settings.navigations);
		menu.findItem(R.id.hideLogo).setChecked(Settings.hideLogo);
	}

	/**
	 * Show message if there are no internet connection
	 */
	private void showToast() {
		Toast.makeText(this, getString(R.string.check_internet_conn),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Check if there are internet connection
	 * 
	 * @return true if there are internet connection
	 */
	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		return (cm.getActiveNetworkInfo() != null);
	}
}