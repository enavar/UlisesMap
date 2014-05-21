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
 * User
 * Class to model of database table user   
 *
 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;

public class User {
	
	/** Field name of the table user from the database */
	public static final String FIELD_NAME = "user";
	/** Field email of the table user from the database */
	public static final String FIELD_MAIL = "email";
	/** Field password of the table user from the database */
	public static final String FIELD_PSW = "password";	
	
	/** Name of user */
	private String name;
	/** Passwod of user */
	private String password;
	
	/**
	 * Constructor
	 * 
	 * @param name name of user
	 * @param password password of user
	 */
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name name of user
	 */
	public User(String name) {
		this.name = name;
	}
	
	/**
	 * Get of password
	 * 
	 * @return password of user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set of password
	 * 
	 * @param password of user
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Get of name
	 * 
	 * @return name of user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Checks if a user exists in the database.
	 * 
	 * @param nameUser name of user
	 * @param password password of user
	 * @return true if exist user or false if not exist user.
	 */
	public static boolean existLogin(String nameUser, String password) {
		String response = null;
		try {
			Client client = new Client(Client.SERVLET_CHECK_USER, true);
			String user = Converter.convertUserToJSONObject(nameUser, password);
			response = client.execute(user).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}		
		return response.equals(Client.TRUE_CHECK);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
