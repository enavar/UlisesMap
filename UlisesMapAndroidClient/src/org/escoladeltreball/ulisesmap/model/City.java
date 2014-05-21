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
 * City
 * Class to model of database table city    
 *
 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class City {
	
	/** Field name of the table city from the database */
	public static final String FIELD_NAME = "name";
	/** Field country of the table city from the database */
	public static final String FIELD_COUNTRY = "country";
	/** Field primary key of the table city from the database */
	public static final String FIELD_PRIMARY_KEY = "ref";
	
	/** Refence of city. It is value unique */
	private String ref;
	/** Name of city */
	private String name;
	/** Country of city */
	private String country;
	
	/**
	 * Constructor 
	 * 
	 * @param ref reference of city
	 * @param name name of city
	 * @param country country of city
	 */
	public City(String ref, String name, String country) {
		this.ref = ref;
		this.name = name;
		this.country = country;
	}
	
	/**
	 * Constructor
	 * 
	 * @param ref reference of city
	 * @param name name of city
	 */
	public City(String ref, String name) {
		this.ref = ref;
		this.name = name;
	}
	
	/** 
	 * Get of name
	 * 
	 * @return name of city
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set of name
	 * 
	 * @param name name of city
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get of Country
	 * 
	 * @return country of city
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Set of Country
	 * 
	 * @param country country of city
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Get of reference
	 * 
	 * @return reference
	 */
	public String getRef() {
		return ref;
	}	
}
