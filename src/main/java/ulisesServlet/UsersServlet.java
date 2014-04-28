package ulisesServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;

import net.sf.json.JSONObject;
import wiamDB.Users;

/*
 * UsersServlet    
 *
 * Copyright 2014 local 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/UsersServlet")
public class UsersServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public Connection con;
	private DAOconection databaseDAO = new DAOconection();
		
	public UsersServlet() {
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
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// database conection 
			Users u = new Users();
			u.connect(this.con);
			// type of response dates
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			// input client dates
			String userName = request.getPart("user").toString();
			String pass = request.getPart("password").toString();
			System.out.println("" + userName + ":" + pass);
			// check if user exists in db
			boolean exist = false;
			try {
				exist = u.selectUserByName(userName,pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (exist) {
				// accio depenent de comment o valoration
			} else {
				u.insertUser(userName,pass);
			}
			// output data
			PrintWriter out = response.getWriter();
			out.print(exist);
			out.flush();
		}

}
