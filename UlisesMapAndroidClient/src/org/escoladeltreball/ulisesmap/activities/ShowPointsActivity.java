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

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowPointsAdapter;
import org.escoladeltreball.ulisesmap.model.City;
import org.escoladeltreball.ulisesmap.model.Point;

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

public class ShowPointsActivity extends BaseActivity implements OnClickListener {

	private ArrayList<Point> points;
	private ShowPointsAdapter adapter;
	private Button map;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showpoints);
		Bundle bundle = getIntent().getExtras();
		points = (ArrayList<Point>) getIntent().getSerializableExtra(Point.FIELD_LIST);
		TextView title = (TextView) findViewById(R.id.Textzone);
		String nameCity = bundle.getString(City.FIELD_NAME);
		title.setText(nameCity);
		map = (Button) findViewById(R.id.toMap);
		ListView list = (ListView) findViewById(R.id.listView1);
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter = new ShowPointsAdapter(getResources(),points,
				layoutInflater);
		list.setAdapter(adapter);
		list.setTextFilterEnabled(true);
		map.setOnClickListener(this);
	}
	
	/* Inner class */
	
	private class IntentLauncher extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... s) {
			ArrayList<Point> selectedPoints = new ArrayList<Point>(adapter.getPointsCheck());
			Intent intent = new Intent(map.getContext(), MapActivity.class);
			intent.putExtra("selectedPoints", selectedPoints);
			startActivity(intent);
			return null;
		}
		@Override
	    protected void onPostExecute(String result) {
			progress.dismiss();
		}		
	}
	
	/* Interface method */

	@Override
	public void onClick(View v) {
		if (adapter.getPointsCheck().size() >= 2) {
			progress.show();
			new IntentLauncher().execute();
		} else
			Toast.makeText(this, R.string.no_selected_point, Toast.LENGTH_LONG).show();
	}
}
