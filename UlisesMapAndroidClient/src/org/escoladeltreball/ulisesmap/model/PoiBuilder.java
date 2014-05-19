package org.escoladeltreball.ulisesmap.model;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.util.GeoPoint;

import android.os.AsyncTask;

/**
 * Async task to get the poi in a separate thread.
 */
public class PoiBuilder extends AsyncTask<Object, Void,  ArrayList<POI>> {
	
	String key;
	GeoPoint start;
	

	public PoiBuilder(String key, GeoPoint start) {
		super();
		this.key = key;
		this.start = start;
	}



	@Override
	protected ArrayList<POI> doInBackground(Object... params) {
		NominatimPOIProvider poiProvider = new NominatimPOIProvider();
		//poiProvider.setService(NominatimPOIProvider.MAPQUEST_POI_SERVICE);
		ArrayList<POI> pois = poiProvider.getPOICloseTo(start, key, 10, 0.01);
		return pois;
	}
	
	public ArrayList<POI> loadPoi() {
		ArrayList<POI> pois = null;
		try {
			pois = this.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pois;
	}

}
