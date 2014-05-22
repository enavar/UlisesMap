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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.City;
import org.escoladeltreball.ulisesmap.model.Point;
import org.escoladeltreball.ulisesmap.model.Route;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * MenuActivity Show a menu with the different options. The activity allowed to
 * go to the ShowRoutesActivity and ShowPointsActivity by country and city.
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class MenuActivity extends BaseActivity implements OnClickListener,
		OnItemSelectedListener {

	/** Button of activity points */
	private Button btnPoints;
	/** Button of activity routes */
	private Button btnRoutes;
	/** Spinner of countries */
	private Spinner spCountries;
	/** Spinner of cities of country */
	private Spinner spCities;
	/** List of names countries */
	private String[] countries;
	/** list of names cities */
	private String[] namesCities;
	/** List of cities object */
	private ArrayList<City> cities;
	/** Lista de routes or points */
	private ArrayList<Object> arrayObject;
	/** Primary key of city selected */
	private String pkCity = null;
	/** name of city selected */
	private String nameCity = null;

	/**
	 * Create MenuActivity
	 * Gets the countries and cities connecting to the database.
	 * Add listeners to buttons and list countries.
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		btnPoints = (Button) findViewById(R.id.buttonPoint);
		btnRoutes = (Button) findViewById(R.id.buttonRoute);
		spCountries = (Spinner) findViewById(R.id.menuCountry);
		spCities = (Spinner) findViewById(R.id.menuCity);
		cities = new ArrayList<City>();
		getCountries();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter adapterCountries = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, countries);
		spCountries.setAdapter(adapterCountries);
		spCountries.setOnItemSelectedListener(this);
		btnPoints.setOnClickListener(this);
		btnRoutes.setOnClickListener(this);

	}
	
	/**
	 * Method is called by the listener when the user clicks to buttons.
	 * If click button points, then it go to ShowPointsActivity and if click 
	 * button routes, then it go to ShowRoutesActivity. Before launching an 
	 * activity check if data is available. If no data displays an informational message.
	 * 
	 * @param v button of routes or button of points
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		if (pkCity != null) {
			arrayObject = (ArrayList<Object>) ((v.equals(btnPoints)) ? getPoints()
					: getRoutes());
			if (arrayObject != null && arrayObject.size() > 0) {
				progress.show();
				new IntentLauncher().execute(v);
			} else if (v.equals(btnPoints))
				Toast.makeText(this, R.string.not_points, Toast.LENGTH_LONG)
						.show();
			else
				Toast.makeText(this, R.string.not_routes, Toast.LENGTH_LONG)
						.show();
		}
	}
	
	/**
	 * IntentLaucher 
	 * Class that launches a background activity.
	 * 
	 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
	 * @version: 1.0
	 */
	private class IntentLauncher extends AsyncTask<View, Void, String> {
		
		/**
		 * Launch ShowPointsActivity or ShowRoutesActivity.
		 * launches ShowPointsActivity or ShowRoutesActivity as a function of 
		 * the clicked button. Send name of city and list points or routes to
		 * next activity.
		 */
		@Override
		protected String doInBackground(View... views) {
			Intent intent;
			if (views[0].equals(btnPoints)) {
				intent = new Intent(views[0].getContext(),
						ShowPointsActivity.class);
				intent.putExtra(Point.FIELD_LIST, arrayObject);
			} else {
				intent = new Intent(views[0].getContext(),
						ShowRoutesActivity.class);
				intent.putExtra(Route.FIELD_LIST, arrayObject);
			}
			intent.putExtra(City.FIELD_NAME, nameCity);
			startActivity(intent);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
		}
	}

	/**
	 * Get and display all countries from database
	 */
	private void getCountries() {
		Client client = new Client(Client.SERVLET_COUNTRIES, false);
		try {
			String response = client.execute().get();
			countries = Converter.convertStringToCountry(response);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get and display all the countries cities from database
	 */
	private void getCities(String nameCountry) {
		Client client = new Client(Client.SERVLET_CITIES, true);
		try {
			String response = client.execute(nameCountry).get();
			cities = Converter.convertStrintToCities(response);
			namesCities = new String[cities.size()];
			for (int i = 0; i < cities.size(); i++)
				namesCities[i] = cities.get(i).getName();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get and display all the city routes from database
	 * 
	 * @return routes
	 */
	private ArrayList<Route> getRoutes() {
		Client client = new Client(Client.SERVLET_ROUTES, true);
		ArrayList<Route> routes = new ArrayList<Route>();
		try {
			String arrayRoutes = client.execute(
					Converter.convertSpaceToBar(pkCity)).get();
			routes = Converter.convertStringToRoutes(arrayRoutes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return routes;
	}

	/**
	 * Get and display all the city points from database
	 * 
	 * @return points
	 */
	private ArrayList<Point> getPoints() {
		Client client = new Client(Client.SERVLET_POINT, true);
		ArrayList<Point> points = new ArrayList<Point>();
		try {
			String response = client.execute(
					Converter.convertSpaceToBar(pkCity)).get();
			points = Converter.convertStringToPoints(response);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return points;
	}
	
	/**
	 * Method is called by the listener when the user choose country.
	 * Change the list of cities on the country chosen. 
	 * Connect to the database to get the list of cities.
	 * 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent.equals(spCountries)) {
			String country = Converter.convertSpaceToBar(countries[position]);
			getCities(country);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			ArrayAdapter adapterCities = new ArrayAdapter(this,
					android.R.layout.simple_spinner_item, namesCities);
			spCities.setAdapter(adapterCities);
			spCities.setOnItemSelectedListener(this);
			pkCity = cities.get(0).getRef();
			nameCity = cities.get(0).getName();
		} else {
			if (pkCity != null) {
				pkCity = cities.get(position).getRef();
				nameCity = cities.get(position).getName();
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}