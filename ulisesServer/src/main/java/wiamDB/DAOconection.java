package wiamDB;

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
	public static final String USERNAME = System.getenv("OPENSHIFT_POSTGRESQL_DB_USERNAME");
	public static final String PASSWORD = System.getenv("OPENSHIFT_POSTGRESQL_DB_PASSWORD");
	public static final String HOST = System.getenv("OPENSHIFT_POSTGRESQL_DB_HOST");
	public static final String PORT = System.getenv("OPENSHIFT_POSTGRESQL_DB_PORT");
	public static final String DBNAME = System.getenv("OPENSHIFT_APP_NAME");
	
	
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
		String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DBNAME;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(
					url, USERNAME, PASSWORD); // ("base de datos","usuario","pasword")
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
