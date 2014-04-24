package org.escoladeltreball.ulisesmap;

import org.osmdroid.util.GeoPoint;

public class Itinerary {
	
	String name;
	GeoPoint gp;
	String image;
	
	/* Constructor */
	
	
	public Itinerary(String name, GeoPoint gp) {
		super();
		this.name = name;
		this.gp = gp;
	}

	/* Getters and Setters */

	public String getName() {
		return name;
	}


	public void setName(String name) {
		name = name;
	}


	public GeoPoint getGp() {
		return gp;
	}


	public void setGp(GeoPoint gp) {
		this.gp = gp;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}
	
	

}
