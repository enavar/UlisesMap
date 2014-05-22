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
 * CommentValoration
 * Class to model of database table comment and valoration.
 *
 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class CommentValoration {
	
	/** Field user of the table comment or valoration from the database */
	public static final String FIELD_USER = "user";
	/** Field value ranking of the table valoration from the database */
	public static final String FIELD_VALUE = "valoration";
	/** Field definition of the table comment from the database */
	public static final String FIELD_COMMENT= "comment";
	
	/** Value ranking. The value can be from 1 to 5. */
	private int val;
	/** Definition of comment */
	private String com;
	/** name of user of valoration */
	private String user;
	/** Maximum value of valoration */
	private static final int MAX_VALUE = 5;
	/** Minimum value of valoration */
	private static final int MIN_VALUE = 1;
	
	/**
	 * Constructor 
	 * 
	 * @param val value ranking. The value can be from 1 to 5. 
	 * @param com definition of comment
	 * @param user name of user
	 */
	public CommentValoration(int val, String com, String user) {
		setVal(val);
		this.com = com;
		this.user = user;
	}
	
	/**
	 * Constructor
	 */
	public CommentValoration() {
		
	}
	
	/**
	 * Get of value ranking
	 * 
	 * @return value ranking
	 */
	public int getVal() {
		return val;
	}
	
	/**
	 * Set of value ranking
	 * 
	 * @param val value ranking
	 */
	public void setVal(int val) {
		if (val < MIN_VALUE)
			this.val = MIN_VALUE;
		else if (val > MAX_VALUE)
			this.val = MAX_VALUE;
		else
			this.val = val;
	}
	
	/**
	 * Get of definition of comment
	 * 
	 * @return definition of comment
	 */
	public String getCom() {
		return com;
	}
	
	/**
	 * Set of definition of comment
	 * 
	 * @param com definition of comment
	 */
	public void setCom(String com) {
		this.com = com;
	}
	
	/**
	 * Get of name of user
	 * 
	 * @return name of user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Set of name of user
	 * 
	 * @param user name of user
	 */
	public void setUser(String user) {
		this.user = user;
	}	
}
