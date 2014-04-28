package wiamDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.sf.json.JSONObject;

/*
 * Valoration    
 *
 * Copyright 2014 local 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class Comments {
	
private Connection con;
	
	/**
	 * Method to set database connection
	 * @param e is a database connection
	 */
	
	public void connect(Connection e) {
		this.con = e;
	}
	
	/**
	 * Select query of table comments
	 * @param routeName the name of selected route
	 * @return array of JSONObject result of query
	 * @throws SQLException
	 */
	
	public ArrayList <JSONObject> selectComments(String routeName) throws SQLException {
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
