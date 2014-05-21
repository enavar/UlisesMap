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
 * Comment
 * Class to model of database table comment
 *
 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class Comment {
	
	/** Position of comment from Arraylist that servlet send. */
	public static final int POSITION_COMMENT = 0;
	/** Field definition of the table comment from the database */
	public static final String FIELD_DEFITION = "def";
	/** Field foreign key route of the table comment from the database */
	public static final String FIELD_ROUTE = "fk_route";
	/** Field foreign key user of the table comment from the database */
	public static final String FIELD_USER = "fk_user";
	
	/** Definition of comment */
	private String definition;
	/** User of comment */
	private User user;
	/** Route of comment */
	private Route route;
	
	/**
	 * Constructor
	 * 
	 * @param definition definition of comment
	 * @param user user of comment
	 * @param route route of comment
	 */
	public Comment(String definition, User user, Route route) {
		this.definition = definition;
		this.user = user;
		this.route = route;
	}
	
	/**
	 * Constructor
	 * 
	 * @param definition definition of comment
	 * @param user user of comment
	 */
	public Comment(String definition, User user) {
		this.definition = definition;
		this.user = user;
	}
	
	/**
	 * Get of definition
	 * 
	 * @return definition of comment
	 */
	public String getDefinition() {
		return definition;
	}
	
	/**
	 * Set of definition
	 * 
	 * @param definition new definition of comment
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	/**
	 * Get of user
	 * 
	 * @return user of comment
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Get of route
	 * 
	 * @return route of comment
	 */
	public Route getRoute() {
		return route;
	}
}
