package ulisesServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * DAOconection    
 *
 * Copyright 2014 local 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class DAOconection {
	
	private Connection con = null;
	
	/**
	 * @return the conexion
	 */
	public Connection getCon() {
		return con;
	}

	/**	
	 * Method to set database connection
	 * @param e is a database connection
	 * @throws ClassNotFoundException 
	 */
	
	public void connect() throws ClassNotFoundException {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(
					"postgresql://$OPENSHIFT_POSTGRESQL_DB_HOST:$OPENSHIFT_POSTGRESQL_DB_PORT", "admint9aazjs", "uBs7mUXIGbGU"); // ("base de datos","usuario","pasword")
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		System.out.println("Conexion realizado");
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
