package ulisesServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wiamDB.Points;
import net.sf.json.JSONObject;

/*
 * PointsServlet    
 *
 * Copyright 2014 local 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class PointsServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection con;
	private DAOconection databaseDAO = new DAOconection();
		
	public PointsServlet() {
		      super();
		    }
		/**
		 * Iniciar la conexion
		 */
	    public void init() {
			
	    	try {
				databaseDAO.connect();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			con = databaseDAO.getCon();	
	    }

	    /**
		 * @throws SQLException 
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
			Points p = new Points();
			p.connect(con);
			// type of response dates
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			// input client dates
			
			// output server dates
			ArrayList<JSONObject> points = p.selectPoints();
			PrintWriter out = response.getWriter();
			for (int i = 0; i < points.size(); i++) {
				out.print(points.get(i));
			}
			out.flush();
		}



}
