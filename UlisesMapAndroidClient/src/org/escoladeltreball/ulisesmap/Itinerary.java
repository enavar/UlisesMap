package org.escoladeltreball.ulisesmap;

import org.osmdroid.util.GeoPoint;

public class Itinerary {
	
	String Name;
	GeoPoint gp;
	String image;
	
	/* Constructor */
	
	
	public Itinerary(String name, GeoPoint gp) {
		super();
		Name = name;
		this.gp = gp;
	}

	/* Getters and Setters */

	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
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
