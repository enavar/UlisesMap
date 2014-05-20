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

public class MenuActivity extends BaseActivity implements OnClickListener, OnItemSelectedListener {
	
	private Button btnPoints;
	private Button btnRoutes;
	private Spinner spCountries;
	private Spinner spCities;
	private String [] countries;
	private String [] namesCities;
	private ArrayList<City> cities;
	private ArrayList<Object> arrayObject;
	private String pkCity = null;
	private String nameCity = null;

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
		ArrayAdapter adapterCountries = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);
		spCountries.setAdapter(adapterCountries);
		spCountries.setOnItemSelectedListener(this);
		btnPoints.setOnClickListener(this);
		btnRoutes.setOnClickListener(this);	

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		if (pkCity != null) {
			arrayObject = (ArrayList<Object>) ((v.equals(btnPoints)) ? getPoints() : getRoutes());
			if (arrayObject!= null && arrayObject.size() > 0) {
				progress.show();
				new IntentLauncher().execute(v);
			} else if (v.equals(btnPoints))
				Toast.makeText(this, R.string.not_points, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(this, R.string.not_routes, Toast.LENGTH_LONG).show();
		}
	}
	
	private class IntentLauncher extends AsyncTask<View, Void, String> {
		
		@Override
		protected String doInBackground(View... views) {
			Intent intent;
			if (views[0].equals(btnPoints)) {
				 intent = new Intent(views[0].getContext(), ShowPointsActivity.class);
				 intent.putExtra(Point.FIELD_LIST, arrayObject);
			} else  {
				intent = new Intent(views[0].getContext(), ShowRoutesActivity.class);
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
	
	private void getCities(String nameCountry) {
		Client client = new Client(Client.SERVLET_CITIES, true);
		try {
			String response = client.execute(nameCountry).get();
			cities = Converter.convertStrintToCities(response);
			namesCities = new String [cities.size()];
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
	 * @return points
	 */
	private ArrayList<Point> getPoints() {
		Client client = new Client(Client.SERVLET_POINT, true);
		ArrayList<Point> points = new ArrayList<Point>();
		try {
			String response = client.execute(Converter.convertSpaceToBar(pkCity)).get();
			points = Converter.convertStringToPoints(response);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return points;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent.equals(spCountries)) {
			String country = Converter.convertSpaceToBar(countries[position]);
			getCities(country);
			@SuppressWarnings({ "rawtypes", "unchecked" })
			ArrayAdapter adapterCities = new ArrayAdapter(this, android.R.layout.simple_spinner_item, namesCities);
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