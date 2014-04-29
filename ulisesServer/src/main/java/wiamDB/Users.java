
package wiamDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Users
 * 
 * @Author: Oleksander Dovbysh
 * 			Elisabet Navarro
 * 			Sheila Perez
 * 
 * This is free software, licensed under the GNU General Public License v3. See
 * http://www.gnu.org/licenses/gpl.html for more information.
 */

public class Users extends DAOconection {
	
	private Connection con;
	private DAOconection databaseDAO = new DAOconection();
	
	/**
	 * Method to set database connection
	 * 
	 * @throws ClassNotFoundException 
	 */
	
	public void connect() throws ClassNotFoundException {
		databaseDAO.connect();
		con = databaseDAO.getCon();
	}
	
	/**
	 * Select query of table users
	 * 
	 * @param name the user name
	 * @param pass the user password
	 * @return true if user exists, false oterwhise
	 * @throws SQLException
	 */
	
	public boolean selectUserByName(String name, String pass) throws SQLException {
		Statement stm;
		try {
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery("Select * from users where name=" + name + " and password=" + pass + ";");
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Insert new user in database
	 * @param name the String name of an user
	 * @param password the String password for an user
	 */
	public void insertUser(String name, String password) {
		Statement stm;
		String insert = "insert into users(name,password) values ('" + name + "','" + password + "')";
		try {
			stm = con.createStatement();
			stm.executeUpdate(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
