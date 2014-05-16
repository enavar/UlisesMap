package org.escoladeltreball.ulisesmap.activities;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.City;

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

public class MenuActivity extends BaseActivity implements OnClickListener, OnItemSelectedListener {
	
	private Button btnPoints;
	private Button btnRoutes;
	private Spinner spCountries;
	private Spinner spCities;
	private String [] countries;
	private String [] namesCities;
	private ArrayList<City> cities;
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
		ArrayAdapter adapterCountries = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);
		spCountries.setAdapter(adapterCountries);
		spCountries.setOnItemSelectedListener(this);
		btnPoints.setOnClickListener(this);
		btnRoutes.setOnClickListener(this);	

	}

	@Override
	public void onClick(View v) {
		if (pkCity != null) {
			progress.show();
			new IntentLauncher().execute(v);
		}
	}
	
	private class IntentLauncher extends AsyncTask<View, Void, String> {
		
		@Override
		protected String doInBackground(View... v) {
			View view = v[0];
			Intent intent = (view.equals(btnPoints)) ? 
					new Intent(view.getContext(), ShowPointsActivity.class) : 
					new Intent(view.getContext(), ShowRoutesActivity.class);
			intent.putExtra(City.FIELD_PRIMARY_KEY, pkCity);
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

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent.equals(spCountries)) {
			String country = Converter.convertSpaceToBar(countries[position]);
			getCities(country);
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