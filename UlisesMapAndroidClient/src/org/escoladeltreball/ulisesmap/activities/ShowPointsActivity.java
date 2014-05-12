package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.BaseActivity;
import org.escoladeltreball.ulisesmap.MapActivity;
import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowPointsAdapter;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.City;
import org.escoladeltreball.ulisesmap.model.Point;
import org.osmdroid.util.GeoPoint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ShowPointsActivity extends BaseActivity implements OnClickListener {

	private ArrayList<Point> points;
	private ArrayList<GeoPoint> selectedPoints;
	private Button map;
	private String pk_city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showpoints);
		Bundle bundle = getIntent().getExtras();
		pk_city = bundle.getString(City.FIELD_PRIMARY_KEY);
		String nameCity = bundle.getString(City.FIELD_NAME);
		map = (Button) findViewById(R.id.toMap);
		TextView title = (TextView) findViewById(R.id.Textzone);
		title.setText(nameCity);
		getPoints();
		ListView list = (ListView) findViewById(R.id.listView1);
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ShowPointsAdapter adapter = new ShowPointsAdapter(getResources(),points,
				layoutInflater);
		list.setAdapter(adapter);
		list.setTextFilterEnabled(true);
		map.setOnClickListener(this);
	}

	/* Methods */

	/**
	 * Create an array with selected points
	 */
	private void getSelectedPoints() {
		selectedPoints = new ArrayList<GeoPoint>();
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			if (p.isSelected()) {
				selectedPoints.add(p.getGp());
			}
		}
	}

	private void getPoints() {
		Client client = new Client(Client.SERVLET_POINT, true);
		try {
			String response = client.execute(pk_city).get();
			points = Converter.convertStringToPoints(response);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		getSelectedPoints();
		Intent intent = new Intent(map.getContext(), MapActivity.class);
		intent.putExtra("selectedPoints", selectedPoints);
		startActivity(intent);		
	}

}
