package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowRoutesAdapter;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.City;
import org.escoladeltreball.ulisesmap.model.Point;
import org.escoladeltreball.ulisesmap.model.Route;
import org.escoladeltreball.ulisesmap.model.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowRoutesActivity extends BaseActivity implements OnClickListener {
	
	private ArrayList<Route> routes;
	private String pkCity;
	private Button info;
	private Button map;
	private String routeName = "museum route";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showroutes);
		Bundle bundle = getIntent().getExtras();
		pkCity = bundle.getString(City.FIELD_PRIMARY_KEY);
		String nameCity = bundle.getString(City.FIELD_NAME);
		TextView city = (TextView) findViewById(R.id.city);
		city.setText(nameCity);
		info = (Button)findViewById(R.id.button_info_route);
		map = (Button) findViewById(R.id.toMapRoute);
		info.setOnClickListener(this);
		map.setOnClickListener(this);
		ListView list = (ListView) findViewById(R.id.listViewRoutes);
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		getRoutes();
		ShowRoutesAdapter adapter = new ShowRoutesAdapter(routes, layoutInflater);
		list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
	}
	
	/* Inner class */
	
	private class IntentLauncher extends AsyncTask<Intent, Void, String> {
		
		@Override
		protected String doInBackground(Intent... i) {
			startActivity(i[0]);
			return null;
		}
		
		 @Override
	        protected void onPostExecute(String result) {
			 progress.dismiss();
	        }		
	}
	
	/* Methods */
	
	/**
	 * Get and display all the city routes from database
	 */
	private void getRoutes() {
		Client client = new Client(Client.SERVLET_ROUTES, true);
		try {
			String arrayRoutes = client.execute(pkCity).get();
			routes = Converter.convertStringToRoutes(arrayRoutes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// capturar el nom de la ruta
		if (routeName.equals("false")) {
			Toast.makeText(this, R.string.noSelectedRoute, Toast.LENGTH_SHORT).show();
		} else {
			clickRoute(v);
		}
	}
	
	/**
	 * Starts a new activity for show comments and valorations
	 */
	private void intentShowCommentsActivity() {
		Intent intent = new Intent(this, ShowCommentsActivity.class);		
		new IntentLauncher().execute(intent);
	}
	
	private void clickRoute(View v) {
		if (v.equals(info)) {
			Settings.routeName = routeName;
			intentShowCommentsActivity();
		} else {
			progress.show();
			//Starts a new activity to show gps map route
			Intent intent = new Intent(v.getContext(), MapActivity.class);
			intent.putExtra("activity", 2);
			intent.putExtra("selectedPoints", getPointsOfRoute());
			new IntentLauncher().execute(intent);
		}
	}
	
	private ArrayList<Point> getPointsOfRoute() {
		Client client = new Client(Client.SERVLET_POINTS_OF_ROUTE, true);
		ArrayList<Point> pointsOfRoute = null;
		try {
			Log.d("route : ", routeName);
			String arrayPoints = client.execute(routeName).get();
			Log.d("route points: ", arrayPoints);
			pointsOfRoute = Converter.convertStringToPointsOfRoutes(arrayPoints);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return pointsOfRoute;
	}
}
