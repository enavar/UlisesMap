package org.escoladeltreball.ulisesmap;

import org.osmdroid.util.GeoPoint;

public class Point {
	
	String name;
	GeoPoint gp;
	String image;
	String description;
	
	/* Constructor */
	
		
	public Point(String name, GeoPoint gp, String image, String description) {
		super();
		this.name = name;
		this.gp = gp;
		this.image = image;
		this.description = description;
	}

	public Point(String name, GeoPoint gp) {
		super();
		this.name = name;
		this.gp = gp;
	}

	/* Getters and Setters */
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
