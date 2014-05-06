package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.BaseActivity;
import org.escoladeltreball.ulisesmap.MapActivity;
import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowPointsAdapter;
import org.escoladeltreball.ulisesmap.connections.ClientPointsRoutes;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.Point;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;



public class ShowPointsActivity extends BaseActivity implements OnItemSelectedListener {
	
	private ArrayList<Point> points;
	private ArrayList<GeoPoint> selectedPoints;
	private Button map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showpoints);		
		
		map = (Button) findViewById(R.id.toMap);
		Spinner spinner = (Spinner) findViewById(R.id.zone);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
		        R.array.zone, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(arrayAdapter);
		
		//use for testing
		getPoints();		
		
		ListView list = (ListView) findViewById(R.id.listView1);
        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ShowPointsAdapter adapter = new ShowPointsAdapter(points, layoutInflater);
        list.setAdapter(adapter);

        list.setTextFilterEnabled(true);
        
        //assign listeners
      	spinner.setOnItemSelectedListener(this);
      	map.setOnClickListener(toMapListener);		
	}
	
	/* Listeners and implemented methods */	
	
	OnClickListener toMapListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			getSelectedPoints();
			Intent intent = new Intent(map.getContext(), MapActivity.class);
			intent.putExtra("selectedPoints", selectedPoints);
			startActivity(intent);
		
		}
	};

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
			
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
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
		ClientPointsRoutes client = new ClientPointsRoutes(ClientPointsRoutes.SERVLET_POINTS);
		client.execute();
		String response = client.getResponse();
		points = Converter.convertStringToPoints(response);
		
		/**points = new ArrayList<Point>();		
		GeoPoint gp1 = new GeoPoint(41.4144948, 2.152694);
		GeoPoint gp2 = new GeoPoint(41.3847092, 2.175827);		
		GeoPoint gp3 = new GeoPoint(41.391646, 2.180271);
		GeoPoint gp4 = new GeoPoint(41.4035707, 2.1744722);
		String image = "http://upload.wikimedia.org/wikipedia/commons/thumb/e/ee/Sagrada_Familia_01.jpg/330px-Sagrada_Familia_01.jpg";
		Point pG = new Point("parkGuell", gp1, null, null, "http://wiam-ulisesmap.rhcloud.com/images/catedral.jpg", "desc");
		Point cathedral = new Point("Cathedral", gp2, null, null, "http://wiam2-ulisesmap.rhcloud.com/images/catedral.jpg", "desc");
		Point arc = new Point("Arc de Triomf", gp3, null, null, "http://wiam2-ulisesmap.rhcloud.com/images/arc.jpg", "desc");
		Point sf = new Point("Sagrada Familia", gp4, null, null, image, "desc");
		
		points.add(pG);
		points.add(cathedral);
		points.add(arc);
		points.add(sf);**/
	}


}
