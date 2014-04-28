package org.escoladeltreball.ulisesmap;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.model.Point;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;

public class MapActivity extends Activity {
	
	//for testing
	Point sf;
	Point cathedral;
	Point arc;

	//for MapView
	Road road;
	MapView map;
	Polyline roadOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		//get an array with points from ShowPointsActivity
		ArrayList<Point> selectedPoints = (ArrayList<Point>) getIntent().getSerializableExtra("selectedPoints");
		
		//get instance of a map
		map = (MapView) findViewById(R.id.mapView);	
		
		//configure a map
		map.setTileSource(TileSourceFactory.MAPNIK);
		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);
		
		//create Points
		instanceObjects();

		//set zoom and centered a map
		IMapController mapController = map.getController();
		mapController.setZoom(14);
		mapController.setCenter(selectedPoints.get(0).getGp());
		
		//draw the road
		getRoadAsync(selectedPoints.get(0).getGp(), selectedPoints.get(1).getGp());

	}

	/**
	 * Async task to get the road in a separate thread.
	 */
	private class UpdateRoadTask extends AsyncTask<Object, Void, Road> {
		protected Road doInBackground(Object... params) {
			@SuppressWarnings("unchecked")
			ArrayList<GeoPoint> waypoints = (ArrayList<GeoPoint>) params[0];

			RoadManager roadManager = new OSRMRoadManager();
	
			return roadManager.getRoad(waypoints);
		}

		protected void onPostExecute(Road result) {
			road = result;
			updateUIWithRoad(result);
		}
	}
	
	/**
	 * Update the map when the calculation of teh road is over
	 * 
	 * @param road
	 */
	void updateUIWithRoad(Road road) {

		if (road.mStatus == Road.STATUS_OK)
			Toast.makeText(map.getContext(),
					"We have a problem to get the route", Toast.LENGTH_SHORT)
					.show();
		roadOverlay = RoadManager.buildRoadOverlay(road, map.getContext());
		map.getOverlays().add(roadOverlay);
		map.invalidate();

	}

	/**
	 * Start the Async task to get a road
	 * 
	 * @param start
	 * @param destination
	 */
	public void getRoadAsync(GeoPoint start, GeoPoint destination) {
		ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>(2);
		waypoints.add(start);
		// intermediate waypoints can be added here:
		// waypoints.add(new GeoPoint(cathedral.getGp()));
		waypoints.add(destination);
		new UpdateRoadTask().execute(waypoints);
	}

	/**
	 * Create some Points
	 */
	public void instanceObjects() {
		GeoPoint gp1 = new GeoPoint(41.403, 2.174);
		GeoPoint gp2 = new GeoPoint(41.383, 2.176);
		GeoPoint gp3 = new GeoPoint(41.391, 2.180);
		
		sf = new Point("Sagrada Familia", gp1);
		cathedral = new Point("Cathedral", gp2);
		arc = new Point("Arc de Trium", gp3);
	}

}

