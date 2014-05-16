package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;
import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.GPSTracker;
import org.escoladeltreball.ulisesmap.model.MarkerBuilder;
import org.escoladeltreball.ulisesmap.model.Point;
import org.escoladeltreball.ulisesmap.model.RoadBuilder;
import org.escoladeltreball.ulisesmap.model.Settings;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class MapActivity extends BaseActivity {

	// for MapView
	private Road road;
	private Road roadGps;
	private MapView map;
	private Polyline roadOverlay;
	private Polyline roadOverlayGps;
	private GPSTracker tracker;
	private RoadBuilder roadBuilder;
	IMapController mapController;
	private static final int ACTIVITY_POINTS = 1;

	private Button prevStep;
	private Button nextStep;

	private ArrayList<GeoPoint> geoPointsToDraw;
	private ArrayList<Point> selectedPoints;

	private int mapElements;
	private int navigationElements;
	private int currentNavigation;

	@Override
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

		int activity = getIntent().getIntExtra("activity", ACTIVITY_POINTS);
		// get an array with points from ShowPointsActivity
		selectedPoints = (ArrayList<Point>) getIntent().getSerializableExtra(
				"selectedPoints");
		geoPointsToDraw = getGeoPoints(selectedPoints);
		if (activity == ACTIVITY_POINTS && geoPointsToDraw.size() > 2) {
			roadBuilder = new RoadBuilder(geoPointsToDraw, false);
		} else {
			roadBuilder = new RoadBuilder(geoPointsToDraw, true);
		}
		road = roadBuilder.getRoad();
		// instantiate other items on the map
		updateUIWithRoad(roadOverlay, road, Color.BLUE);
		initMapItems();
		currentNavigation = mapElements;
	}

	protected void initMapItems() {
		// show Poins of interest on the map
		makePointsMarkers(selectedPoints);
		mapElements = map.getOverlays().size();
		// Show user current position and draw a route to a start point
		if (Settings.gps) {
			initGPS();
		}
		// Show instructions for each step of the road
		if (Settings.navigations) {
			currentNavigation = -1;
			makeNavigationMarkers(road);
			navigationElements = map.getOverlays().size() - mapElements;
			if (Settings.gps) {
				makeNavigationMarkers(roadGps);
			}
		}
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
				initMapItems();
				updateUIWithRoad(roadOverlay, road, Color.BLUE);
			} else {
				item.setChecked(true);
				Settings.navigations = true;
				prevStep.setVisibility(View.VISIBLE);
				nextStep.setVisibility(View.VISIBLE);
				makeNavigationMarkers(road);
				navigationElements = map.getOverlays().size() - mapElements;
				if (Settings.gps) {
					makeNavigationMarkers(roadGps);
				}
			}
			return true;
		case R.id.car:
			return changeRouteType(item);
		case R.id.bicycle:
			return changeRouteType(item);
		case R.id.walk:
			return changeRouteType(item);
		case R.id.myGPS:
			if (item.isChecked()) {
				item.setChecked(false);
				Settings.gps = false;
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

	public boolean changeRouteType(MenuItem item) {
		changeRouteStatus(item);
		map.getOverlays().clear();
		map.invalidate();
		roadBuilder = new RoadBuilder(geoPointsToDraw, true);
		road = roadBuilder.getRoad();
		initMapItems();
		updateUIWithRoad(roadOverlay, road, Color.BLUE);
		return true;
	}

	/**
	 * Update the map when the calculation of the road is over
	 * 
	 * @param road
	 */
	void updateUIWithRoad(Polyline polyline, Road road, int color) {

		if (road.mStatus != Road.STATUS_OK)
			Toast.makeText(
					map.getContext(),
					"We have a problem to get the route from your current location",
					Toast.LENGTH_SHORT).show();
		else {
			polyline = RoadManager.buildRoadOverlay(road, map.getContext());
			polyline.setColor(color);
			map.getOverlays().add(polyline);

			// set zoom and centered a map
			mapController = map.getController();
			mapController.setZoom(14);
			mapController.setCenter(geoPointsToDraw.get(0));
			map.invalidate();
		}

	}

	public void makeNavigationMarkers(Road road) {
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

	public void makePointsMarkers(ArrayList<Point> selectedPoints) {
		for (int i = 0; i < selectedPoints.size(); i++) {
			Point point = selectedPoints.get(i);
			MarkerBuilder nodeMarker = new MarkerBuilder(map, getResources(),
					point.getGp(), point.getImage(), point.getName(),
					point.getDescription());
			map.getOverlays().add(nodeMarker.build());
		}
	}

	public void initGPS() {
		tracker = new GPSTracker(this, map);
		GeoPoint myLocation = null;
		if (tracker.isNetworkEnabled() == false) {
			tracker.showSettingsAlert();
		} else {
			myLocation = new GeoPoint(tracker.getLatitude(),
					tracker.getLongitude());
			showRoutefromMyCurrentLocation(myLocation, geoPointsToDraw.get(0));
		}
	}

	public void showRoutefromMyCurrentLocation(GeoPoint a, GeoPoint b) {
		roadGps = null;
		ArrayList<GeoPoint> ar = new ArrayList<GeoPoint>();
		ar.add(a);
		ar.add(b);
		roadBuilder = new RoadBuilder(geoPointsToDraw, true);
		road = roadBuilder.getRoad();
		updateUIWithRoad(roadOverlayGps, roadGps, Color.GREEN);

	}

	public ArrayList<GeoPoint> getGeoPoints(ArrayList<Point> points) {
		ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
		for (int i = 0; i < points.size(); i++) {
			geoPoints.add(points.get(i).getGp());
		}
		return geoPoints;
	}
	
	public void showMarkerInfo() {
		Marker m = (Marker) map.getOverlays().get(currentNavigation);
		mapController.setCenter(m.getPosition());
		m.showInfoWindow();
	}

	OnClickListener Navigationlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if ((Button) v == prevStep) {
				if (currentNavigation >= mapElements) {
					currentNavigation--;
					showMarkerInfo();
				} else {
					currentNavigation = mapElements;
					showMarkerInfo();
				}
			} else {
				int maxNavigation = mapElements + navigationElements;
				if (currentNavigation < maxNavigation) {
					showMarkerInfo();
					currentNavigation++;
				} else {
					currentNavigation = mapElements + navigationElements -1;
					showMarkerInfo();
				}
			}
		}
	};
}
