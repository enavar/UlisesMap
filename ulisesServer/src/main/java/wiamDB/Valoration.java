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

public class Valoration {
	
	private Connection con;
	private DAOconection databaseDAO = new DAOconection();
	
	/**
	 * Method to set database connection
	 * @throws ClassNotFoundException 
	 */
	
	public void connect() throws ClassNotFoundException {
		databaseDAO.connect();
		con = databaseDAO.getCon();
	}
	
	/**
	 * Select query of table valoracio
	 * @param routeName the name of selected route
	 * @return array of JSONObject result of query
	 * @throws SQLException
	 * @throws JSONException 
	 */
	
	public ArrayList <JSONObject> selectValoration(String routeName) throws SQLException, JSONException {
		Statement stm;
		ArrayList <JSONObject> arr = new ArrayList <JSONObject>();
		try {
			stm = con.createStatement();
			ResultSet rs = stm
					.executeQuery("Select * from valoracio where fk_route=" + routeName + ";");
			while (rs.next()) {
				JSONObject json = new JSONObject();
				 json.put("value", rs.getInt("def"));
				 json.put("user",rs.getString("fk_user"));
				 arr.add(json);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arr;
	}
	
	/**
	 * Insert new valoration in database
	 * @param valoracio int number of valoration
	 * @param user String name of user who valorated
	 * @param route String name of valorated route
	 */
	public void insertValoration(int valoracio,String user,String route) {
		Statement stm;
		String insert = "insert into valoration values (" + valoracio + ",'" + route + "','" + user + "')";
		try {
			stm = con.createStatement();
			stm.executeUpdate(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if determinated user has valored a determinated route
	 * @param routeName the string name of a route
	 * @param userName the string name who valorates
	 * @return return true if user has valorated the route, false otherwise
	 * @throws SQLException
	 */
	public boolean checkValoration(String routeName,String userName) throws SQLException {
		Statement stm;
		try {
			stm = con.createStatement();
			ResultSet rs = stm
					.executeQuery("Select * from valoration where fk_route=" + routeName + " and fk_user=" + userName + ";");
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
