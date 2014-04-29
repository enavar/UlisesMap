package wiamDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;



/*
 * Valoration    
 *
 * @Author: Oleksander Dovbysh
 * 			Elisabet Navarro
 * 			Sheila Perez
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class Comments {
	
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
	 * Select query of table comments
	 * @param routeName the name of selected route
	 * @return array of JSONObject result of query
	 * @throws SQLException
	 * @throws JSONException 
	 */
	
	public ArrayList <JSONObject> selectComments(String routeName) throws SQLException, JSONException {
		Statement stm;
		ArrayList <JSONObject> arr = new ArrayList <JSONObject>();
		try {
			stm = con.createStatement();
			ResultSet rs = stm
					.executeQuery("Select * from comments where fk_route=" + routeName + ";");
			while (rs.next()) {
				JSONObject json = new JSONObject();
				 json.put("value", rs.getString("def"));
				 json.put("user",rs.getString("fk_user"));
				 arr.add(json);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arr;
	}
	
	/**
	 * Insert comment in database
	 * @param comment the string comment for insert
	 * @param user the name of user who inserts the comment
	 * @param route the name of the route for comment
	 */
	public void insertComment(String comment,String user,String route) {
		Statement stm;
		String insert = "insert into comments values (" + comment + ",'" + route + "','" + user + "')";
		try {
			stm = con.createStatement();
			stm.executeUpdate(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if a user has a comment in determinated route
	 * @param routeName the string name of route 
	 * @param userName the user name who inserte the comment
	 * @return true if comment exists, false otherwish
	 * @throws SQLException
	 */
	public boolean checkComment(String routeName,String userName) throws SQLException {
		Statement stm;
		try {
			stm = con.createStatement();
			ResultSet rs = stm
					.executeQuery("Select * from comments where fk_route=" + routeName + " and fk_user=" + userName + ";");
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
