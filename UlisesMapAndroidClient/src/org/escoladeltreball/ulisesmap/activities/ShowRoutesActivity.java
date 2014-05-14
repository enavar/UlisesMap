package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.BaseActivity;
import org.escoladeltreball.ulisesmap.MapActivity;
import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowRoutesAdapter;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.City;
import org.escoladeltreball.ulisesmap.model.Point;
import org.escoladeltreball.ulisesmap.model.Route;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
        Button toMap = (Button) findViewById(R.id.toMapRoute);
        toMap.setOnClickListener(listener);
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
	
	private ArrayList<Point> getPointsOfRoute() {
		Client client = new Client(Client.SERVLET_POINTS_OF_ROUTE, true);
		ArrayList<Point> pointsOfRoute = null;
		try {
			Log.d("route : ", routes.get(0).getName());
			String arrayPoints = client.execute(routes.get(0).getName()).get();
			Log.d("route points: ", arrayPoints);
			pointsOfRoute = Converter.convertStringToPointsOfRoutes(arrayPoints);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return pointsOfRoute;
	}
	
	OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent(v.getContext(), MapActivity.class);
			intent.putExtra("activity", 2);
			intent.putExtra("selectedPoints", getPointsOfRoute());
			startActivity(intent);
		}
	};

}
