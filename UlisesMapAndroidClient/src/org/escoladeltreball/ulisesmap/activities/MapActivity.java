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
import org.escoladeltreball.ulisesmap.model.GPSTracker;
import org.escoladeltreball.ulisesmap.model.MarkerBuilder;
import org.escoladeltreball.ulisesmap.model.PoiBuilder;
import org.escoladeltreball.ulisesmap.model.Point;
import org.escoladeltreball.ulisesmap.model.RoadBuilder;
import org.escoladeltreball.ulisesmap.model.Settings;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.overlays.FolderOverlay;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * MapActivity
 * Show a route from an array of points according to chosen option
 * in menu. Depending on activity that started this one an array of points may
 * be ordered for improve a result route The different options handled by this
 * activity lets to show changes on the map at run time, like route type,
 * navigation marker o user location.
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class MapActivity extends BaseActivity {

	/** view for show open street maps in android */
	private MapView map;
	/** map settings */
	private IMapController mapController;
	/** route builded from points of interest */
	private Road road;
	/** routed builded from current user position and start point of the route */
	private Road roadGps;
	/** a line drawn from correspond route */
	private Polyline roadOverlay;
	/** a line drawn from correspond route */
	private Polyline roadOverlayGps;
	/** constantly check user current position */
	private GPSTracker tracker;
	/** build a road from points of interest */
	private RoadBuilder roadBuilder;

	/** identification number of activity which started current activity */
	public static final int ACTIVITY_POINTS = 1;
	/** key of list points of before activity */
	public static final String SELECT_POINTS= "selectedPoints";
	/** key of identification of before activity */
	public static final String TYPE_ACTIVITY = "activity";

	/** button for navigate throw markers */
	private Button prevStep;
	/** button for navigate throw markers */
	private Button nextStep;

	/** points selected by user o owned from chosen route */
	private ArrayList<Point> selectedPoints;
	/** coordinates of points of interest */
	private ArrayList<GeoPoint> geoPointsToDraw;

	/** number of map primary elements */
	private int mapElements;
	/** number of map navigation elements */
	private int navigationElements;
	/** number of current navigation marker */
	private int currentNavigation;

	@Override
	/**
	 * Initiate a map and prepare  all item which may be used.
	 * Get a set of points from a father activity and build a route.
	 * Shown other items related to a road according to a user's options
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		prevStep = (Button) findViewById(R.id.prevStep);
		nextStep = (Button) findViewById(R.id.nextStep);
		prevStep.setOnClickListener(Navigationlistener);
		nextStep.setOnClickListener(Navigationlistener);

		// get instance of a map
		map = (MapView) findViewById(R.id.mapView);
		// configure a map
		map.setTileSource(TileSourceFactory.MAPNIK);
		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);

		int activity = getIntent().getIntExtra(TYPE_ACTIVITY, ACTIVITY_POINTS);
		// get an array with points from ShowPointsActivity
		selectedPoints = (ArrayList<Point>) getIntent().getSerializableExtra(
				SELECT_POINTS);
		geoPointsToDraw = getGeoPoints(selectedPoints);
		if (activity == ACTIVITY_POINTS && geoPointsToDraw.size() > 2) {
			geoPointsToDraw = RoadBuilder.orderGeoPoints(geoPointsToDraw);
		}
		RoadBuilder roadBuilder = new RoadBuilder(geoPointsToDraw);
		road = roadBuilder.getRoad();
		// instantiate other items on the map
		updateUIWithRoad(roadOverlay, road, Color.BLUE);
		initMapItems();
	}

	/**
	 * Initiate a map elements	
	 */
	protected void initMapItems() {
		// show Poins of interest on the map
		makePointsMarkers(selectedPoints);
		// Show user current position and draw a route to a start point
		if (Settings.gps) {
			initGPS();
		}
		mapElements = map.getOverlays().size();
		// Show instructions for each step of the road
		if (Settings.navigations) {
			if (Settings.gps) {
				makeNavigationMarkers(roadGps);
			}
			prevStep.setVisibility(View.VISIBLE);
			nextStep.setVisibility(View.VISIBLE);
			currentNavigation = -1;
			makeNavigationMarkers(road);
			navigationElements = map.getOverlays().size() - mapElements;
		}
		currentNavigation = mapElements - 1;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.navigations:
			if (item.isChecked()) {
				item.setChecked(false);
				Settings.navigations = false;
				map.getOverlays().clear();
				map.invalidate();
				updateUIWithRoad(roadOverlay, road, Color.BLUE);
				initMapItems();
			} else {
				item.setChecked(true);
				Settings.navigations = true;
				prevStep.setVisibility(View.VISIBLE);
				nextStep.setVisibility(View.VISIBLE);
				makeNavigationMarkers(road);
				if (Settings.gps) {
					makeNavigationMarkers(roadGps);
				}
				map.invalidate();
				navigationElements = map.getOverlays().size() - mapElements;
			}
			return true;
		case R.id.car:
			changeRouteStatus(item);
			return updateMap(item);
		case R.id.bicycle:
			changeRouteStatus(item);
			return updateMap(item);
		case R.id.walk:
			changeRouteStatus(item);
			return updateMap(item);
		case R.id.walk_transport:
			changeRouteStatus(item);
			return updateMap(item);
		case R.id.myGPS:
			if (item.isChecked()) {
				item.setChecked(false);
				Settings.gps = false;
				map.getOverlays().clear();
				map.invalidate();
				roadBuilder = new RoadBuilder(geoPointsToDraw);
				road = roadBuilder.getRoad();
				updateUIWithRoad(roadOverlay, road, Color.BLUE);
				initMapItems();
			} else {
				item.setChecked(true);
				Settings.gps = true;
				initGPS();
			}
			return true;

		default:				
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Recalculate a road and all related item when different route type is
	 * selected
	 * 
	 * @param item
	 *            a selected item from menu
	 * @return a true when item is selected
	 */
	public boolean updateMap(MenuItem item) {
		map.getOverlays().clear();
		map.invalidate();
		roadBuilder = new RoadBuilder(geoPointsToDraw);
		road = roadBuilder.getRoad();
		updateUIWithRoad(roadOverlay, road, Color.BLUE);
		initMapItems();
		return true;
	}

	/**
	 * Build a set of object nearest to given object
	 * 
	 * @param geoPoint
	 *            coordinates of point
	 * @param poiMarkers
	 *            nearest object to show
	 * @return an array with configured nearest object
	 */
	public FolderOverlay makePoiMarkers(GeoPoint geoPoint,
			FolderOverlay poiMarkers, String key) {
		PoiBuilder poiBuilder = new PoiBuilder(key, geoPoint);
		ArrayList<POI> pois = poiBuilder.loadPoi();
		Drawable PoiIcon = getResources().getDrawable(R.drawable.metro);
		if (pois != null) {
			for (POI poi : pois) {
				Marker poiMarker = new Marker(map);
				poiMarker.setTitle(poi.mType);
				poiMarker.setSnippet(poi.mDescription.split(",")[0]);
				poiMarker.setPosition(poi.mLocation);					
				poiMarker.setIcon(PoiIcon);
				poiMarkers.add(poiMarker);
			}
		}
		return poiMarkers;

	}

	/**
	 * Update the map when the calculation of the road is over
	 * 
	 * @param road
	 *            a to show at the map
	 */
	public void updateUIWithRoad(Polyline polyline, Road road, int color) {

		if (road.mStatus != Road.STATUS_OK)
			Toast.makeText(this,
					R.string.error_map + "\n" + road.mStatus,
					Toast.LENGTH_SHORT).show();
		else {
			polyline = RoadManager.buildRoadOverlay(road, this);
			polyline.setColor(color);
			map.getOverlays().add(polyline);

			// set zoom and centered a map
			mapController = map.getController();
			mapController.setZoom(14);
			mapController.setCenter(geoPointsToDraw.get(0));
			map.invalidate();
		}

	}

	/**
	 * Add to the map navigation markers to follow a road
	 * 
	 * @param road
	 *            a route
	 */
	public void makeNavigationMarkers(Road road) {
		// set Markers
		Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
		TypedArray iconIds = getResources().obtainTypedArray(
				R.array.navigation_icons);
		for (int i = 0; i < road.mNodes.size(); i++) {
			RoadNode node = road.mNodes.get(i);
			Marker nodeMarker = new Marker(map);
			nodeMarker.setPosition(node.mLocation);
			nodeMarker.setIcon(nodeIcon);
			nodeMarker.setTitle("Step " + (i + 1));
			map.getOverlays().add(nodeMarker);

			// Set the bubble snippet with the instructions:
			nodeMarker.setSnippet(node.mInstructions);

			// Set the bubble sub-description with the length and duration of
			// the step:
			nodeMarker.setSubDescription(Road.getLengthDurationText(
					node.mLength, node.mDuration));

			// And put an icon showing the maneuver at this step:
			int iconId = iconIds.getResourceId(node.mManeuverType,
					R.drawable.ic_empty);
			if (iconId != R.drawable.ic_empty) {
				Drawable icon = getResources().getDrawable(iconId);
				nodeMarker.setImage(icon);
			}
		}
	}

	/**
	 * Show at the map selected points with his own image and description
	 * 
	 * @param selectedPoints
	 *            an array with selected points
	 */
	public void makePointsMarkers(ArrayList<Point> selectedPoints) {
		FolderOverlay poiMarkers = new FolderOverlay(this);
		map.getOverlays().add(poiMarkers);
		for (int i = 0; i < selectedPoints.size(); i++) {
			Point point = selectedPoints.get(i);
			if (Settings.routeType == R.id.walk_transport) {
				makePoiMarkers(point.getGp(), poiMarkers, "station");
			}
			MarkerBuilder nodeMarker = new MarkerBuilder(map, getResources(),
					point.getGp(), point.getImage(), point.getName(),
					point.getDescription());
			map.getOverlays().add(nodeMarker.build());
		}
		map.invalidate();
	}

	/**
	 * find a current user position and show it
	 */
	public void initGPS() {
		tracker = new GPSTracker(this, map);
		GeoPoint myLocation = null;
		if (!tracker.isNetworkEnabled()) {
			tracker.showSettingsAlert();
		} else {
			myLocation = new GeoPoint(tracker.getLatitude(),
					tracker.getLongitude());
			showRoutefromMyCurrentLocation(myLocation, geoPointsToDraw.get(0));
		}
	}

	/**
	 * Show a route from current user position to route start point
	 * 
	 * @param a
	 *            a user current position
	 * @param b
	 *            a start point of teh route
	 */
	public void showRoutefromMyCurrentLocation(GeoPoint a, GeoPoint b) {
		roadGps = null;
		ArrayList<GeoPoint> ar = new ArrayList<GeoPoint>();
		ar.add(a);
		ar.add(b);
		roadBuilder = new RoadBuilder(ar);
		roadGps = roadBuilder.getRoad();
		updateUIWithRoad(roadOverlayGps, roadGps, Color.GREEN);
	}

	/**
	 * Extract geoPoint from given points
	 * 
	 * @param points
	 *            a points
	 * @return a set with geoPoints
	 */
	public ArrayList<GeoPoint> getGeoPoints(ArrayList<Point> points) {
		ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
		for (int i = 0; i < points.size(); i++) {
			geoPoints.add(points.get(i).getGp());
		}
		return geoPoints;
	}

	/**
	 * Centered in navigation marker and show its info
	 */
	public void showMarkerInfo() {
		Marker m = (Marker) map.getOverlays().get(currentNavigation);
		mapController.setCenter(m.getPosition());
		mapController.setZoom(19);
		m.showInfoWindow();
	}

	/**
	 * Listener to navigate throw navigation markers
	 */
	private OnClickListener Navigationlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (road.mStatus == Road.STATUS_OK) {
				return;
			}
			if (((Button) v).equals(prevStep)) {
				currentNavigation = (currentNavigation > mapElements) ? currentNavigation - 1 : mapElements + navigationElements - 1;
			} else {
				int maxNavigation = mapElements + navigationElements;
				currentNavigation = (currentNavigation < maxNavigation - 1) ? currentNavigation + 1 : mapElements;
			}
			showMarkerInfo();
		}
	};
}
