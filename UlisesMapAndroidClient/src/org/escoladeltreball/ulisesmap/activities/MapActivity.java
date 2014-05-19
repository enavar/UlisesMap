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

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * MapActivity Show a route from points of interest
 * 
 * @author: Oleksander Dovbysh Elisabet Navarro Sheila Perez
 * 
 *          This is free software, licensed under the GNU General Public License
 *          v3. See http://www.gnu.org/licenses/gpl.html for more information.
 */

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
		Log.d("selected Pointa", "" + selectedPoints.size());
		geoPointsToDraw = getGeoPoints(selectedPoints);
		Log.d("geoPointsToDraw", "" + geoPointsToDraw.size());
		if (activity == ACTIVITY_POINTS && geoPointsToDraw.size() > 2) {
			geoPointsToDraw = RoadBuilder.orderGeoPoints(geoPointsToDraw);
		}
		RoadBuilder roadBuilder = new RoadBuilder(geoPointsToDraw);
		road = roadBuilder.getRoad();
		// instantiate other items on the map
		updateUIWithRoad(roadOverlay, road, Color.BLUE);
		Log.d("road", "" + map.getOverlays().size());
		initMapItems();
	}

	protected void initMapItems() {
		// show Poins of interest on the map
		makePointsMarkers(selectedPoints);
		// Show user current position and draw a route to a start point
		if (Settings.gps) {
			initGPS();
		}
		Log.d("points", "" + map.getOverlays().size());
		mapElements = map.getOverlays().size();
		Log.d("mapele", "" + mapElements);
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
		currentNavigation = mapElements;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("menu", "" + item.getItemId());
		switch (item.getItemId()) {

		case R.id.navigations:
			Log.d("menu", "navigations");
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
			Log.d("menu", "car");
			changeRouteStatus(item);
			return updateMap(item);
		case R.id.bicycle:
			Log.d("menu", "bicycle");
			changeRouteStatus(item);
			return updateMap(item);
		case R.id.walk:
			Log.d("menu", "walk");
			changeRouteStatus(item);
			return updateMap(item);
		case R.id.walk_transport:
			Log.d("menu", "walk_transport");
			changeRouteStatus(item);
			return updateMap(item);
		case R.id.myGPS:
			Log.d("menu", "myGPS");
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
			Log.d("menu", "def");
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Recalculate a road and all related item when diferent route type is
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
	 * @return an array with configured  nearest object
	 */
	public FolderOverlay makePoiMarkers(GeoPoint geoPoint,
			FolderOverlay poiMarkers, String key) {
		PoiBuilder poiBuilder = new PoiBuilder(key , geoPoint);
		ArrayList<POI> pois = poiBuilder.loadPoi();
		Drawable PoiIcon = getResources().getDrawable(R.drawable.metro);
		if (pois != null) {
			Log.d("size poi", "" + pois.size());

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
	 * @param road a to show at the map
	 */
	void updateUIWithRoad(Polyline polyline, Road road, int color) {

		if (road.mStatus != Road.STATUS_OK)
			Toast.makeText(map.getContext(),
					"We have a problem to get the route\n" + road.mStatus,
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

	/**
	 * Add to the map navigation markers to follow a road
	 * 
	 * @param road a route
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
	 * @param selectedPoints an array with selected points
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
		if (tracker.isNetworkEnabled() == false) {
			tracker.showSettingsAlert();
		} else {
			myLocation = new GeoPoint(tracker.getLatitude(),
					tracker.getLongitude());
			Log.d("gps", myLocation.toString());
			Log.d("start", geoPointsToDraw.get(0).toString());
			showRoutefromMyCurrentLocation(myLocation, geoPointsToDraw.get(0));
		}
	}

	/**
	 * Show a route from current user position to route start point
	 * 
	 * @param a a user current position
	 * @param b a start point of teh route
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
	 * @param points a points
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
	OnClickListener Navigationlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (road.mStatus == Road.STATUS_OK) {
				if ((Button) v == prevStep) {
					if (currentNavigation > mapElements) {
						currentNavigation--;
						showMarkerInfo();
					} else {
						currentNavigation = mapElements + navigationElements
								- 1;
						showMarkerInfo();
					}
				} else {
					Log.d("cur", "" + currentNavigation);
					int maxNavigation = mapElements + navigationElements;
					if (currentNavigation < maxNavigation) {
						showMarkerInfo();
						currentNavigation++;
					} else {
						currentNavigation = mapElements;
						showMarkerInfo();
					}
				}
			}
		}
	};
}
