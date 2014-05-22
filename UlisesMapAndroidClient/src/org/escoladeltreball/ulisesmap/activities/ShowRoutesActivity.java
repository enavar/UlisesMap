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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ShowRoutesActivity
 * Show all routes available for chosen city
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class ShowRoutesActivity extends BaseActivity implements OnClickListener {

	/** a set of routes of choosen city */
	private ArrayList<Route> routes;
	/** button to start a comment/valoration activity */
	private Button info;
	/** button to show a route at the map */
	private Button map;
	/** a list with all routes */
	ListView list;
	/** a name route chosed by user */
	private String routeName;

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * initiate all object when the activity is started
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showroutes);
		routes = (ArrayList<Route>) getIntent().getSerializableExtra(
				Route.FIELD_LIST);
		Bundle bundle = getIntent().getExtras();
		String nameCity = bundle.getString(City.FIELD_NAME);
		TextView city = (TextView) findViewById(R.id.city);
		city.setText(nameCity);
		info = (Button) findViewById(R.id.button_info_route);
		map = (Button) findViewById(R.id.toMapRoute);
		info.setOnClickListener(this);
		map.setOnClickListener(this);
		list = (ListView) findViewById(R.id.listViewRoutes);
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ShowRoutesAdapter adapter = new ShowRoutesAdapter(routes,
				layoutInflater, getResources());
		list.setAdapter(adapter);
		list.setTextFilterEnabled(true);
	}

	@Override
	public void onClick(View v) {
		routeName = getCheckedItem();
		if (routeName == null) {
			Toast.makeText(this, R.string.noSelectedRoute, Toast.LENGTH_SHORT)
					.show();
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

	/**
	 * Determine which button is clicked and start correspond activity by creating
	 * new Intentlauncher
	 * 
	 * @param v view of clicked button
	 */
	private void clickRoute(View v) {
		if (v.equals(info)) {
			Settings.routeName = routeName;
			intentShowCommentsActivity();
		} else {
			progress.show();
			// Starts a new activity to show gps map route
			Intent intent = new Intent(v.getContext(), MapActivity.class);
			intent.putExtra("activity", 2);
			intent.putExtra("selectedPoints", getPointsOfRoute());
			new IntentLauncher().execute(intent);
		}
	}

	/**
	 * Download a set of routes from a server
	 * 
	 * @return a set of routes
	 */
	private ArrayList<Point> getPointsOfRoute() {
		Client client = new Client(Client.SERVLET_POINTS_OF_ROUTE, true);
		ArrayList<Point> pointsOfRoute = null;
		try {
			String arrayPoints = client.execute(routeName).get();
			pointsOfRoute = Converter.convertStringToPoints(arrayPoints);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return pointsOfRoute;
	}

	/**
	 * Determine which route is selected by user
	 * 
	 * @return a selected route, null otherwise
	 */
	private String getCheckedItem() {
		int checkedId = list.getCheckedItemPosition();
		if (checkedId != -1) {
			Route route = (Route) list.getItemAtPosition(checkedId);
			return route.getName();
		}
		return null;
	}
}
