package org.escoladeltreball.ulisesmap.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.R.drawable;
import org.escoladeltreball.ulisesmap.R.id;
import org.escoladeltreball.ulisesmap.R.layout;
import org.escoladeltreball.ulisesmap.adapters.ImageDownloader;
import org.escoladeltreball.ulisesmap.model.GPSTracker;
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
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MapActivity extends BaseActivity {

	// for MapView
	private RoadManager roadManager;
	private Road road;
	private Road roadGps;
	private MapView map;
	private Polyline roadOverlay;
	private Polyline roadOverlayGps;
	private GPSTracker tracker;
	private RoadBuilder roadBuilder;
	private static final int ACTIVITY_POINTS = 1;

	private ArrayList<GeoPoint> geoPointsToDraw;
	private ArrayList<Point> selectedPoints;
	private final int IMAGE_SIZE = 50;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

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

		// Points Activity
		if (activity == ACTIVITY_POINTS) {
			roadBuilder = new RoadBuilder(geoPointsToDraw, false);
			if (geoPointsToDraw.size() > 2) {
				roadBuilder.orderGeoPoints();
			}
		} else {
			roadBuilder = new RoadBuilder(geoPointsToDraw, true);
		}
		road = roadBuilder.getRoad();
		// instantiate other items on the map
		initMapItems();
		updateUIWithRoad(roadOverlay, road, Color.BLUE);

	}

	protected void initMapItems() {
		// show Poins of interest on the map
		makePointsMarkers(selectedPoints);
		// Show user current position and draw a route to a start point
		if (Settings.gps) {
			initGPS();
		}
		// Show instructions for each step of the road
		if (Settings.navigations) {
			makeNavigationMarkers(road);
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
				makeNavigationMarkers(road);
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
			IMapController mapController = map.getController();
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
		Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_a);
		for (int i = 0; i < selectedPoints.size(); i++) {
			Point point = selectedPoints.get(i);
			Marker nodeMarker = new Marker(map);
			nodeMarker.setPosition(point.getGp());
			ImageDownloader ig = new ImageDownloader();
			try {
				Bitmap bitmap = ig.execute(point.getImage()).get();
				Bitmap cutted = cutImage(bitmap);
				Drawable smallImg = new BitmapDrawable(getResources(),
						Bitmap.createScaledBitmap(cutted, IMAGE_SIZE,
								IMAGE_SIZE, false));
				Drawable image = new BitmapDrawable(getResources(), bitmap);
				nodeMarker.setIcon(smallImg);
				nodeMarker.setImage(image);
				nodeMarker.setSubDescription(point.getDescription());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			// nodeMarker.setIcon(nodeIcon);
			nodeMarker.setTitle(point.getName());
			map.getOverlays().add(nodeMarker);
		}
	}

	public Bitmap cutImage(Bitmap input) {
		Bitmap output = Bitmap.createBitmap(input.getWidth(),
				input.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, input.getWidth(), input.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(input.getWidth() / 2, input.getHeight() / 2,
				input.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(input, rect, rect, paint);
		return output;
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

}
