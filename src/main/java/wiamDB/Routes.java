package wiamDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.sf.json.JSONObject;

/*
 * Routes    
 *
 * Copyright 2014 local 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class Routes {

private Connection con;
	
/**
 * Method to set database connection
 * @param e is a database connection
 */

public void connect(Connection e) {
	this.con = e;
}

/**
 * Select query of table routes
 * @param routeName the name of selected route
 * @return JSONObject result of query
 * @throws SQLException
 */

public JSONObject selectRoutesInfo(String routeName) throws SQLException {
	Statement stm;
	JSONObject json = new JSONObject();
	try {
		stm = con.createStatement();
		ResultSet rs = stm
				.executeQuery("Select * from routes where name=" + routeName + ";");
		while (rs.next()) {
			 json.put("value", rs.getString("name"));
			 json.put("user",rs.getString("description"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return json;
}
}
