package ulisesServlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;



/*
 * RoutesServlet    
 *
 * @Author: Oleksander Dovbysh
 * 			Elisabet Navarro
 * 			Sheila Perez
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class RoutesServlet {
	
	private static final long serialVersionUID = 1L;
		
	public RoutesServlet() {
		      super();
		    }
		/**
		 * Iniciar la conexion
		 */
	    public void init() {
			
	    }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// type of response dates
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		// input client dates
		int id;
		// output server dates
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		out.print(json);
		out.flush();
	}

}
