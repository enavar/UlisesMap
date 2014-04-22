package org.escoladeltreball.ulisesmap;

import java.util.ArrayList;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.RoadManager;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {
	
	Itinerary sf;
	Itinerary cathedral;
	Itinerary arc;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MapView map = (MapView) findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(41.403, 2.174);
        IMapController mapController = map.getController();
        mapController.setZoom(14);
        mapController.setCenter(startPoint);
        
       /* RoadManager roadManager = new OSRMRoadManager();
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(sf.getGp());
        waypoints.add(cathedral.getGp());
        waypoints.add(arc.getGp());*/
        
		
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
