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

import java.io.Serializable;
/**
 * Route
 * Class to model of database table route    
 *
 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
@SuppressWarnings("serial")
public class Route implements Serializable {
	
	/** Field name of the table route from the database */
	public static final String FIELD_NAME = "name";
	/** Field description of the table route from the database */
	public static final String FIELD_DESCRIPTION = "description";
	/** Field average ranking of the table route from the database */
	public static final String FIELD_AVG = "avg";
	/** Field image of the table route from the database */
	public static final String FIELD_IMAGE = "image";
	/** Name of arrayList route that activities send */
	public static final String FIELD_LIST = "routes";
	
	/** Name of route */
	private String name;
	/** Description of route */
	private String description;
	/** Image of route */
	private String image;
	/** Average ranking of route */
	private double valorationAverage;
	/** State of route */
	private boolean selected;
		
	/**
	 * Constructor
	 * 
	 * @param name name of route
	 * @param description description of route
	 * @param valorationAverage average ranking of route
	 */
	public Route(String name, String description, double valorationAverage) {
		this.name = name;
		this.description = description;
		this.valorationAverage = valorationAverage;
		this.selected = false;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name name of route
	 * @param description description of route
	 * @param valorationAverage average ranking of route
	 * @param image image of route
	 */
	public Route(String name, String description, double valorationAverage, String image) {
		this.name = name;
		this.description = description;
		this.valorationAverage = valorationAverage;
		this.image = image;
		this.selected = false;
	}
	
	/**
	 * Get of image
	 * 
	 * @return path of route's image
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * Set of image
	 * 
	 * @param image path of route's image
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * Get of name
	 * 
	 * @return name of route
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set of name
	 * 
	 * @param name name of route
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get of description
	 * 
	 * @return description of route
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set of description
	 * 
	 * @param description description of route
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get of average ranking
	 * 
	 * @return average ranking of route
	 */
	public double getValorationAverage() {
		return valorationAverage;
	}
	
	/**
	 * Set of selected
	 * 
	 * @param selected state of route
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * Get of selected
	 * 
	 * @return state of route
	 */
	public boolean isSelected() {
		return selected;
	}
}
