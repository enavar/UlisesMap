package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.BaseActivity;
import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowRoutesAdapter;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.City;
import org.escoladeltreball.ulisesmap.model.Route;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

public class ShowRoutesActivity extends BaseActivity {
	
	private ArrayList<Route> routes;
	private String pk_city;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showroutes);
		Bundle bundle = getIntent().getExtras();
		pk_city = bundle.getString(City.FIELD_PRIMARY_KEY);
		String nameCity = bundle.getString(City.FIELD_NAME);
		ListView list = (ListView) findViewById(R.id.listViewRoutes);
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		getRoutes();
		ShowRoutesAdapter adapter = new ShowRoutesAdapter(routes, layoutInflater);
		list.setAdapter(adapter);
        	list.setTextFilterEnabled(true);
	}
	
	private ArrayList<Route> getRoutes() {
		Client client = new Client(Client.SERVLET_ROUTES, true);
		try {
			String arrayRoutes = client.execute(pk_city).get();
			routes = Converter.convertStringToRoutes(arrayRoutes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return routes;
	}

}
