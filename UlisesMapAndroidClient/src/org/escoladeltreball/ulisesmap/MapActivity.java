package org.escoladeltreball.ulisesmap;

import java.util.ArrayList;

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

	Itinerary sf;
	Itinerary cathedral;
	Itinerary arc;

	Road road;
	MapView map;
	Polyline roadOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		map = (MapView) findViewById(R.id.mapView);
		map.setTileSource(TileSourceFactory.MAPNIK);
		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);

		GeoPoint startPoint = new GeoPoint(41.403, 2.174);
		IMapController mapController = map.getController();
		mapController.setZoom(14);
		mapController.setCenter(startPoint);
		
		instanceObjects();
		
		getRoadAsync(sf.getGp(), arc.getGp());

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

	void updateUIWithRoad(Road road) {

		if (road.mStatus == Road.STATUS_OK)
			Toast.makeText(map.getContext(),
					"We have a problem to get the route", Toast.LENGTH_SHORT)
					.show();
		roadOverlay = RoadManager.buildRoadOverlay(road, map.getContext());
		map.getOverlays().add(roadOverlay);
		map.invalidate();

	}

	public void getRoadAsync(GeoPoint start, GeoPoint destination) {
		ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>(2);
		waypoints.add(start);
		// intermediate waypoints can be added here:
		// waypoints.add(new GeoPoint(48.226, -1.9456));
		waypoints.add(destination);
		new UpdateRoadTask().execute(waypoints);
	}

	public void instanceObjects() {
		GeoPoint gp1 = new GeoPoint(41.403, 2.174);
		GeoPoint gp2 = new GeoPoint(41.383, 2.176);
		GeoPoint gp3 = new GeoPoint(41.391, 2.180);

		sf = new Itinerary("Sagrada Familia", gp1);
		cathedral = new Itinerary("Cathedral", gp2);
		arc = new Itinerary("Arc de Trium", gp3);
	}

}

