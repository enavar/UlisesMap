package ulisesServlet;

import java.sql.Connection;
import java.sql.SQLException;

/*
 * DAO    
 *
 * Copyright 2014 local 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class DAO {
	
	public Connection con = null;
	
	/**
	 * Method to set database connection
	 * @param e is a database connection
	 */
	
	public void connect(Connection e) {
		this.con = e;
	}
	
	/**
	 * Method to close database conection
	 */
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
