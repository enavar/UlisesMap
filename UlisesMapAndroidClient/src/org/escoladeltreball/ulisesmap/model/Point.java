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
/**
 * Point
 * Class to model of database table point    
 *
 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
import java.io.Serializable;

import org.osmdroid.util.GeoPoint;

@SuppressWarnings("serial")
public class Point implements Serializable {
	
	/** Field name of the table point from the database */
	public static final String FIELD_NAME = "name";
	/** Field latitude of the table point from the database */
	public static final String FIELD_LAT = "lat";
	/** Field longitude of the table point from the database */
	public static final String FIELD_LON = "lon";
	/** Field street of the table point from the database */
	public static final String FIELD_STREET = "street";
	/** Field description of the table point from the database */
	public static final String FIELD_DESCRIPTION = "description";
	/** Field path image of the table point from the database */
	public static final String FIELD_IMAGE = "image";
	/** Field url of the table point from the database */
	public static final String FIELD_URL = "url";
	/** Name of arrayList points that activities send */
	public static final String FIELD_LIST = "points";
	
	/** Name of point */
	private String name;
	/** Coordinate of point */
	private GeoPoint gp;
	/** Street of point */
	private String street;
	/** Description of point */
	private String description;
	/** Image of point */
	private String image;
	/** Url of point */
	private String url;
	/** State of point */
	private boolean selected;

	/**
	 * Constructor
	 * 
	 * @param name name of point
	 * @param gp coordinate of point
	 * @param street street of point
	 * @param description description of point
	 * @param image image of point
	 * @param url url of point
	 */
	public Point(String name, GeoPoint gp, String street, String description, String image, String url) {
		this.name = name;
		this.gp = gp;
		this.image = image;
		this.description = description;
		this.street = street;
		this.url = url;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name name of point
	 * @param gp coordinate of point
	 */
	public Point(String name, GeoPoint gp) {
		this.name = name;
		this.gp = gp;
	}
	
	/**
	 * Constructor
	 * 
	 * @param gp  coordinate of point
	 */
	public Point(GeoPoint gp) {
		super();
		this.gp = gp;
	}

	/**
	 * Get of Selected
	 * 
	 * @return state of point
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Set of Selected
	 * 
	 * @param selected state of point
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * Get of Description
	 * 
	 * @return description of point
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set of description
	 * 
	 * @param description description of point
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Get of name
	 * 
	 * @return name of point
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get of Coordinate
	 * 
	 * @return coordinate of point
	 */
	public GeoPoint getGp() {
		return gp;
	}
	
	/**
	 * Get of Image
	 * 
	 * @return path of point's image
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * Set of Image
	 * 
	 * @param image path of point's image
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * Get of street
	 * 
	 * @return street of point
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * Set of street
	 * 
	 * @param street street of point
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * Get of url
	 * 
	 * @return url of point
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Set of url
	 * 
	 * @param url url of point
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
