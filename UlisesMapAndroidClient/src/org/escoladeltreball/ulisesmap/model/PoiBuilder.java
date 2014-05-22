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
package org.escoladeltreball.ulisesmap.model;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.util.GeoPoint;

import android.os.AsyncTask;

/**
 * PoiBuilder
 * Async task to get the poi in a separate thread.
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class PoiBuilder extends AsyncTask<Object, Void,  ArrayList<POI>> {
	
	/** a key word to load all object of this type */
	private String key;
	/** coordinates to find object near this one */
	private GeoPoint start;
	/** a maximum number of objects to load */
	private final int MAX_OBJECTS = 10;

	/**
	 * Constructor
	 * 
	 * @param key a key word to load all object of this type
	 * @param start coordinates to find object near this one
	 */
	public PoiBuilder(String key, GeoPoint start) {
		super();
		this.key = key;
		this.start = start;
	}

	@Override
	/**
	 * get a set of object in background task
	 */
	protected ArrayList<POI> doInBackground(Object... params) {
		NominatimPOIProvider poiProvider = new NominatimPOIProvider();
		//poiProvider.setService(NominatimPOIProvider.MAPQUEST_POI_SERVICE);
		ArrayList<POI> pois = poiProvider.getPOICloseTo(start, key, MAX_OBJECTS, 0.01);
		return pois;
	}
	
	/**
	 * Execute a background task
	 * 
	 * @return a set of objects
	 */
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
