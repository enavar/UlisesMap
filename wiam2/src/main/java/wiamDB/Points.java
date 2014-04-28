package wiamDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.sf.json.JSONObject;

/*
 * Points    
 *
 * Copyright 2014 local 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class Points {
	
private Connection con;

/**
 * Method to set database connection
 * @param e is a database connection
 */

public void connect(Connection e) {
	this.con = e;
}
	
	/**
	 * Select query of table points
	 * @return Array of JSONObject result of query
	 * @throws SQLException
	 */
	
	public ArrayList<JSONObject> selectPoints() throws SQLException {
		Statement stm;
		ArrayList <JSONObject> arr = new ArrayList <JSONObject>();
		try {
			stm = con.createStatement();
			ResultSet rs = stm
					.executeQuery("Select * from points");
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("pk", rs.getInt("def"));
				json.put("name", rs.getString("name"));
				json.put("lat", rs.getDouble("lat"));
				json.put("lon", rs.getDouble("lon"));
				json.put("text", rs.getString("text"));
				json.put("img",rs.getString("img"));
				arr.add(json);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arr;
	}

}
