package org.escoladeltreball.ulisesmap.model;

import java.io.Serializable;

import org.osmdroid.util.GeoPoint;

@SuppressWarnings("serial")
public class Point implements Serializable {

	public static final String FIELD_NAME = "name";
	public static final String FIELD_LAT = "lat";
	public static final String FIELD_LON = "lon";
	public static final String FIELD_STREET = "street";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_IMAGE = "image";
	public static final String FIELD_URL = "url";
	private static final String URL_SERVER = "http://wiam2-ulisesmap.rhcloud.com/images/";
	private String name;
	private GeoPoint gp;
	private String street;	
	private String description;
	private String image;
	private String url;	
	private boolean selected;

	/* Constructor */

	public Point(String name, GeoPoint gp, String street, String description, String image, String url) {
		super();
		this.name = name;
		this.gp = gp;
		this.image = URL_SERVER + image;
		this.description = description;
		this.street = street;
		this.url = url;
	}

	public Point(String name, GeoPoint gp) {
		super();
		this.name = name;
		this.gp = gp;
	}
	
	public Point(GeoPoint gp) {
		super();
		this.gp = gp;
	}

	/* Getters and Setters */
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
