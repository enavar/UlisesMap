package wiamDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * Routes    
 *
 * @Author: Oleksander Dovbysh
 * 			Elisabet Navarro
 * 			Sheila Perez 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class Routes {

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
	 * Select query of table routes
	 * 
	 * @param routeName the name of selected route
	 * @return JSONObject result of query
	 * @throws SQLException
	 * @throws JSONException
	 */

	public JSONObject selectRoutesInfo(String routeName) throws SQLException,
			JSONException {
		Statement stm;
		JSONObject json = new JSONObject();
		try {
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery("Select * from routes where name="
					+ routeName + ";");
			while (rs.next()) {
				json.put("value", rs.getString("name"));
				json.put("user", rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return json;
	}
}
