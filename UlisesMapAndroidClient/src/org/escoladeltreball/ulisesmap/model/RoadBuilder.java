package org.escoladeltreball.ulisesmap.model;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Async task to get the road in a separate thread.
 */
public class RoadBuilder extends AsyncTask<Object, Void, Road> {

	RoadManager roadManager;
	ArrayList<GeoPoint> waypoints;

	public RoadBuilder(ArrayList<GeoPoint> waypoints) {
		super();
		this.waypoints = waypoints;
	}

	protected Road doInBackground(Object... params) {
		@SuppressWarnings("unchecked")
		ArrayList<GeoPoint> waypoints = (ArrayList<GeoPoint>) params[0];
		Log.d("road async", "" + waypoints.size());
		// Sending request for get specific roadManager
		roadManager = new MapQuestRoadManager(
				"Fmjtd%7Cluubn96y2l%2C8n%3Do5-907a5w");

		if (Settings.routeType == R.id.car) {
			// for quickest drive time route.
		} else if (Settings.routeType == R.id.walk) {
			// for get walking route
			roadManager.addRequestOption("routeType=pedestrian");
		} else if (Settings.routeType == R.id.bicycle) {
			// for get bicycle route
			roadManager.addRequestOption("routeType=bicycle");
		} else if (Settings.routeType == R.id.bicycle) {
			// for get walking and transport route
			roadManager.addRequestOption("routeType=multimodal");
		} else {
			// used if we get null
			roadManager.addRequestOption("routeType=pedestrian");
		}

		return roadManager.getRoad(waypoints);
	}
	
	public Road getRoad() {
		Road road = null;
		try {
			road = this.execute(waypoints).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return road;
	}

	/**
	 * Build the shortest route and start the Async task to get a road *
	 * 
	 * @param selectedPoints
	 *            an array with a geopoints
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static ArrayList<GeoPoint> orderGeoPoints(ArrayList<GeoPoint> waypoints) {
		// orden an array to build the shortest way
		// there is a issue when all the points are choosen. The route
		// doesn't visualize correctly

		ArrayList<GeoPoint> geoPointsToOrder = waypoints;
		waypoints = new ArrayList<GeoPoint>();

		GeoPoint startPoint = getStartPoint(geoPointsToOrder);
		waypoints.add(startPoint);
		geoPointsToOrder.remove(startPoint);
		for (int i = 0; i < geoPointsToOrder.size(); i++) {
			GeoPoint nextPoint = getNearestPoint(startPoint, geoPointsToOrder);
			waypoints.add(nextPoint);
			startPoint = nextPoint;
			geoPointsToOrder.remove(nextPoint);
		}
		waypoints.addAll(geoPointsToOrder);
		return waypoints;
	}

	/**
	 * Calculate a start point of the route. The start point as a point with a
	 * lowest Latitude
	 * 
	 * @param points
	 *            an array with selected points
	 * @return a star point
	 */
	public static GeoPoint getStartPoint(ArrayList<GeoPoint> points) {
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
	public static GeoPoint getNearestPoint(GeoPoint startPoint,
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

}
