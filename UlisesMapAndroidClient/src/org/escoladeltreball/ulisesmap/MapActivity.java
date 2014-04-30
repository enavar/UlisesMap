package org.escoladeltreball.ulisesmap;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.model.Point;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
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
import android.app.Activity;
import android.graphics.drawable.Drawable;

public class MapActivity extends Activity {

	// for testing
	Point sf;
	Point cathedral;
	Point arc;

	// for MapView
	Road road;
	MapView map;
	Polyline roadOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// get an array with points from ShowPointsActivity
		ArrayList<Point> selectedPoints = (ArrayList<Point>) getIntent()
				.getSerializableExtra("selectedPoints");

		// get instance of a map
		map = (MapView) findViewById(R.id.mapView);

		// configure a map
		map.setTileSource(TileSourceFactory.MAPNIK);
		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);

		// set zoom and centered a map
		IMapController mapController = map.getController();
		mapController.setZoom(14);
		mapController.setCenter(selectedPoints.get(0).getGp());

		// draw the road
		if (selectedPoints.size() < 3) {
			GeoPoint startPoint = selectedPoints.get(0).getGp();
			GeoPoint endPoint = selectedPoints.get(selectedPoints.size() - 1)
					.getGp();
			getRoadAsync(startPoint, endPoint);
		} else {
			ArrayList<Point> pointsToDraw = selectedPoints;
			Point startPoint;
			for (int i = 0; i < pointsToDraw.size() - 1; i++) {
				startPoint = getStartPoint(pointsToDraw);
				pointsToDraw.remove(startPoint);
				Point nextPoint = getNearestPoint(startPoint, pointsToDraw);
				getRoadAsync(startPoint.getGp(), nextPoint.getGp());
				startPoint = nextPoint;
				pointsToDraw.remove(nextPoint);			
			}
		}

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
			makeMarkers();
			updateUIWithRoad(result);
		}
	}

	/**
	 * Update the map when the calculation of teh road is over
	 * 
	 * @param road
	 */
	void updateUIWithRoad(Road road) {

		if (road.mStatus != Road.STATUS_OK)
			Toast.makeText(map.getContext(),
					"We have a problem to get the route", Toast.LENGTH_SHORT)
					.show();
		roadOverlay = RoadManager.buildRoadOverlay(road, map.getContext());
		map.getOverlays().add(roadOverlay);
		//map.invalidate();

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

	public void makeMarkers() {
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

	/**
	 * Calculate a start point of the route. The start point as a point with a
	 * lowest Latitude
	 * 
	 * @param points
	 *            an array with selected points
	 * @return a star point
	 */
	public Point getStartPoint(ArrayList<Point> points) {
		Point point = points.get(0);
		for (int i = 1; i < points.size(); i++) {
			Point nextPoint = points.get(i);
			if (point.getGp().getLatitude() > nextPoint.getGp().getLatitude()) {
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
	public Point getNearestPoint(Point startPoint, ArrayList<Point> points) {
		Point nearestPoint = points.get(0);
		int distance1 = startPoint.getGp().distanceTo(nearestPoint.getGp());
		for (int i = 1; i < points.size(); i++) {
			Point nextPoint = points.get(i);
			int distance2 = startPoint.getGp().distanceTo(nextPoint.getGp());
			if (distance1 > distance2) {
				nearestPoint = nextPoint;
			}
		}
		return nearestPoint;

	}
}
