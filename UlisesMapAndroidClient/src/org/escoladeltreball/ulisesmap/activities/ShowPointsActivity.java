package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowPointsAdapter;
import org.escoladeltreball.ulisesmap.model.Point;
import org.osmdroid.util.GeoPoint;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;



public class ShowPointsActivity extends Activity {
	
	ArrayList<Point> points;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showpoints);
		
		//use for testing
		getPoints();		
		//android:name="org.escoladeltreball.ulisesmap.activities.LoginActivity"
		//		android:label="@string/title_activity_login" >
		
		
		ListView list = (ListView) findViewById(R.id.listView1);
        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ShowPointsAdapter adapter = new ShowPointsAdapter();
        list.setAdapter(adapter);

        list.setTextFilterEnabled(true);
		
	}
	
	private void getPoints() {
		points = new ArrayList<Point>();
		
		GeoPoint gp1 = new GeoPoint(41.403, 2.174);
		GeoPoint gp2 = new GeoPoint(41.383, 2.176);
		GeoPoint gp3 = new GeoPoint(41.391, 2.180);
		String image = "http://upload.wikimedia.org/wikipedia/commons/thumb/e/ee/Sagrada_Familia_01.jpg/330px-Sagrada_Familia_01.jpg";
		Point sf = new Point("Sagrada Familia", gp1, image, "desc");
		Point cathedral = new Point("Cathedral", gp2, image, "desc");
		Point arc = new Point("Arc de Trium", gp3, image, "desc");
		
		points.add(sf);
		points.add(cathedral);
		points.add(arc);
		
	}

}
