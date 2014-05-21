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
 * Valoration
 * Class to model of database table valoration  
 *
 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class Valoration {
	
	/** Position of valoration from Arraylist that servlet send. */
	public static final int POSITION_VALORATION = 1;
	/** Field definition of the table valoration from the database */
	public static final String FIELD_VALORATION = "def";
	/** Field foreign key route of the table valoration from the database */
	public static final String FIELD_ROUTE = "fk_route";
	/** Field foreign key user of the table valoration from the database */
	public static final String FIELD_USER = "fk_user";
	
	/** Value ranking. The value can be from 1 to 5. */
	private int valoration;
	/** User of valoration */
	private User user;
	/** Route of valoration */
	private Route route;
	/** Maximum value of valoration */
	private static final int MAX_VALUE = 5;
	/** Minimum value of valoration */
	private static final int MIN_VALUE = 1;
	
	/**
	 * Constructor
	 * 
	 * @param valoration value ranking
	 * @param user user of valoration
	 * @param route route of valoration
	 */
	public Valoration(int valoration, User user, Route route) {
		setValoration(valoration);
		this.user = user;
		this.route = route;
	}
	
	/**
	 * Constructor
	 * 
	 * @param valoration value ranking
	 * @param user user of valoration
	 */
	public Valoration(int valoration, User user) {
		setValoration(valoration);
		this.user = user;
	}
	
	/**
	 * Get of valoration
	 * 
	 * @return value of ranking
	 */
	public int getValoration() {
		return valoration;
	}
	
	/**
	 * Set of valoration.
	 * 
	 * @param valoration Value ranking. The value can be from 1 to 5.
	 */
	public void setValoration(int valoration) {
		if (valoration < MIN_VALUE)
			this.valoration = MIN_VALUE;
		else if (valoration > MAX_VALUE)
			this.valoration = MAX_VALUE;
		else
			this.valoration = valoration;
	}
	
	
	/** 
	 * Get of User
	 * 
	 * @return user of valoration
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Get of route
	 * 
	 * @return route of valoration
	 */
	public Route getRoute() {
		return route;
	}

}
