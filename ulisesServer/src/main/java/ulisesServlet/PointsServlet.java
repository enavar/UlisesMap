package ulisesServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import wiamDB.Points;


/*
 * PointsServlet    
 *
 * @Author: Oleksander Dovbysh
 * 			Elisabet Navarro
 * 			Sheila Perez
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class PointsServlet {
	
	private static final long serialVersionUID = 1L;
		
	public PointsServlet() {
		      super();
		    }
		/**
		 * Iniciar la conexion
		 */
	    public void init() {
			
	    }

	    /**
		 * @throws SQLException 
	     * @throws ClassNotFoundException 
	     * @throws JSONException 
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException, JSONException {
			Points p = new Points();
			p.connect();
			// type of response dates
			response.setContentType("application/json");
			// output server dates
			System.out.println("doGET");
			
			//ArrayList <JSONObject> points = p.selectPoints();
			PrintWriter out = response.getWriter();
			out.print("hola");
			//for (int i = 0; i < points.size(); i++) {
				//out.print(points.get(i));
				//System.out.println(points.get(i));
			//}
			out.flush(); 
		}



}
