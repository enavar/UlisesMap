package org.escoladeltreball.ulisesmap.model;

import org.osmdroid.util.GeoPoint;

public class Point {
	
	private String name;
	private GeoPoint gp;
	private String image;
	
	/* Constructor */
	
	
	public Point(String name, GeoPoint gp) {
		super();
		this.name = name;
		this.gp = gp;
	}

	/* Getters and Setters */

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public GeoPoint getGp() {
		return gp;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}
	
	

}
