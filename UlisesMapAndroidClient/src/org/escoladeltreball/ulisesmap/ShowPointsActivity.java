package org.escoladeltreball.ulisesmap;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.model.Point;
import org.osmdroid.util.GeoPoint;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

public class ShowPointsActivity extends Activity {
	
	ArrayList<Point> points;
	private LayoutInflater layoutInflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//use for testing
		getPoints();		
		//android:name="org.escoladeltreball.ulisesmap.activities.LoginActivity"
		//		android:label="@string/title_activity_login" >
		
		
		
		
	}
	
	private ArrayList<Point> getPoints() {
		points = new ArrayList<Point>();
		
		GeoPoint gp1 = new GeoPoint(41.403, 2.174);
		GeoPoint gp2 = new GeoPoint(41.383, 2.176);
		GeoPoint gp3 = new GeoPoint(41.391, 2.180);

		Point sf = new Point("Sagrada Familia", gp1);
		Point cathedral = new Point("Cathedral", gp2);
		Point arc = new Point("Arc de Trium", gp3);
		
		points.add(sf);
		points.add(cathedral);
		points.add(arc);
		
		return points;
		
	}

}
