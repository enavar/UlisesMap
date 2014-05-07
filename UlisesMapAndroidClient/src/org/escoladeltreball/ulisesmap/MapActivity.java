package org.escoladeltreball.ulisesmap;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.model.GPSTracker;
import org.escoladeltreball.ulisesmap.model.Settings;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class MapActivity extends BaseActivity {

	// for MapView
	RoadManager roadManager;
	Road road;
	MapView map;
	Polyline roadOverlay;
	GPSTracker tracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// get an array with points from ShowPointsActivity
		ArrayList<GeoPoint> selectedPoints = (ArrayList<GeoPoint>) getIntent()
				.getSerializableExtra("selectedPoints");

		// get instance of a map
		map = (MapView) findViewById(R.id.mapView);
		// configure a map
		map.setTileSource(TileSourceFactory.MAPNIK);
		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);

		// set zoom and centered a map
		IMapController mapController = map.getController();
		mapController.setZoom(16);
		mapController.setCenter(selectedPoints.get(0));

		// show Poins of interest on the map
		makePointsMarkers(selectedPoints);

		// Show user current position and draw a route to a start point
		if (Settings.gps) {
			tracker = new GPSTracker(this, map);
			GeoPoint myLocation = null;
			if (tracker.isCanGetLocation() == false) {
				tracker.showSettingsAlert();
			} else {
				myLocation = new GeoPoint(tracker.getLatitude(), tracker.getLongitude());
				showRoutefromMyCurrentLocation(myLocation,selectedPoints.get(0));				
			}
		}

		// draw the road
		try {
			getRoadAsync(selectedPoints);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Async task to get the road in a separate thread.
	 */
	private class UpdateRoadTask extends AsyncTask<Object, Void, Road> {
		protected Road doInBackground(Object... params) {
			@SuppressWarnings("unchecked")
			ArrayList<GeoPoint> waypoints = (ArrayList<GeoPoint>) params[0];

			if (Settings.routeType == R.id.car) {
				// for quickest drive time route.
				roadManager = new OSRMRoadManager();
			} else if (Settings.routeType == R.id.walk) {
				// Sending request for get specific roadManager
				roadManager = new MapQuestRoadManager(
						"Fmjtd%7Cluubn96y2l%2C8n%3Do5-907a5w");
				// for get walking route
				roadManager.addRequestOption("routeType=pedestrian");
			} else if (Settings.routeType == R.id.bicycle) {
				// Sending request for get specific roadManager
				roadManager = new MapQuestRoadManager(
						"Fmjtd%7Cluubn96y2l%2C8n%3Do5-907a5w");
				// for get bicycle route
				roadManager.addRequestOption("routeType=bicycle");
			} else {
				roadManager = new MapQuestRoadManager(
						"Fmjtd%7Cluubn96y2l%2C8n%3Do5-907a5w");
				roadManager.addRequestOption("routeType=pedestrian");
			}

			return roadManager.getRoad(waypoints);
		}

	}

	/**
	 * Update the map when the calculation of the road is over
	 * 
	 * @param road
	 */
	void updateUIWithRoad(Road road, int color) {

		if (road.mStatus != Road.STATUS_OK)
			Toast.makeText(map.getContext(),
					"We have a problem to get the route", Toast.LENGTH_SHORT)
					.show();
		roadOverlay = RoadManager.buildRoadOverlay(road, map.getContext());
		roadOverlay.setColor(color);
		map.getOverlays().add(roadOverlay);
		map.invalidate();

	}

	/**
	 * Build the shortest route and start the Async task to get a road *
	 * 
	 * @param selectedPoints
	 *            an array with a geopoints
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void getRoadAsync(ArrayList<GeoPoint> selectedPoints)
			throws InterruptedException, ExecutionException {
		ArrayList<GeoPoint> pointsToDraw = new ArrayList<GeoPoint>();
		// check if we have just two points
		if (selectedPoints.size() < 3) {
			pointsToDraw.add(selectedPoints.get(0));
			pointsToDraw.add(selectedPoints.get(1));
			// orden an array to build the shortest way
			// there is a issue when all the points are choosen. The route
			// doesn't visualize correctly
		} else {
			GeoPoint startPoint = getStartPoint(selectedPoints);
			pointsToDraw.add(startPoint);
			selectedPoints.remove(startPoint);
			for (int i = 0; i < selectedPoints.size(); i++) {
				GeoPoint nextPoint = getNearestPoint(startPoint, selectedPoints);
				pointsToDraw.add(nextPoint);
				startPoint = nextPoint;
				selectedPoints.remove(nextPoint);
			}
			pointsToDraw.add(selectedPoints.get(0));
		}
		new UpdateRoadTask().execute(pointsToDraw);
		road = new UpdateRoadTask().execute(pointsToDraw).get();
		updateUIWithRoad(road, Color.BLUE);
		makeNavigationMarkers();
	}

	public void makeNavigationMarkers() {
		// set Markers
		Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
		for (int i = 0; i < road.mNodes.size(); i++) {
			RoadNode node = road.mNodes.get(i);
			Marker nodeMarker = new Marker(map);
			nodeMarker.setPosition(node.mLocation);
			nodeMarker.setIcon(nodeIcon);
			nodeMarker.setTitle("Step " + i);
			map.getOverlays().add(nodeMarker);

			// Set the bubble snippet with the instructions:
			nodeMarker.setSnippet(node.mInstructions);

			// Set the bubble sub-description with the length and duration of
			// the step:
			nodeMarker.setSubDescription(Road.getLengthDurationText(
					node.mLength, node.mDuration));

			// And put an icon showing the maneuver at this step:
			Drawable icon = getResources().getDrawable(R.drawable.ic_continue);
			nodeMarker.setImage(icon);
		}
	}

	public void makePointsMarkers(ArrayList<GeoPoint> selectedPoints) {
		Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_a);
		for (int i = 0; i < selectedPoints.size(); i++) {
			Marker nodeMarker = new Marker(map);
			nodeMarker.setPosition(selectedPoints.get(i));
			nodeMarker.setIcon(nodeIcon);
			nodeMarker.setTitle("Point " + (i + 1));
			map.getOverlays().add(nodeMarker);
		}
	}

	/**
	 * Calculate a start point of the route. The start point as a point with a
	 * lowest Latitude
	 * 
	 * @param points
	 *            an array with selected points
	 * @return a star point
	 */
	public GeoPoint getStartPoint(ArrayList<GeoPoint> points) {
		GeoPoint point = points.get(0);
		for (int i = 1; i < points.size(); i++) {
			GeoPoint nextPoint = points.get(i);
			if (point.getLatitude() > nextPoint.getLatitude()) {
				point = nextPoint;
			}
		}
		return point;
	}

	/**
	 * Calculate a nearest point to start point
	 * 
	 * @param startPoint
	 *            a point where the route is start
	 * @param points
	 *            an array with selected points
	 * @return nearest point to start point
	 */
	public GeoPoint getNearestPoint(GeoPoint startPoint,
			ArrayList<GeoPoint> points) {
		GeoPoint nearestPoint = points.get(0);
		int minDistance = startPoint.distanceTo(nearestPoint);
		for (int i = 1; i < points.size(); i++) {
			GeoPoint nextPoint = points.get(i);
			int distance = startPoint.distanceTo(nextPoint);
			if (minDistance > distance) {
				nearestPoint = nextPoint;
				minDistance = distance;
			}
		}
		return nearestPoint;
	}

	public void showRoutefromMyCurrentLocation(GeoPoint a, GeoPoint b) {
		UpdateRoadTask asynctask = new UpdateRoadTask();
		Road road = null;
		ArrayList<GeoPoint> ar = new ArrayList<GeoPoint>();
		ar.add(a);
		ar.add(b);
		try {
			road = asynctask.execute(ar).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateUIWithRoad(road, Color.GREEN);

	}

}
