package wiamDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Users    
 *
 * Copyright 2014 local 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class Users {
	
	private Connection con;
	/**
	 * Select query of table users
	 * @return String query result
	 * @throws SQLException
	 */
	
	public ArrayList<String> clients() throws SQLException {
		ArrayList<String> users = new ArrayList<String>();
		try {
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;

	}

}
