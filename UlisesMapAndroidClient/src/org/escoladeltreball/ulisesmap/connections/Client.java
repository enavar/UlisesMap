/**
 * Copyright (c) 2014, Oleksander Dovbysh & Elisabet Navarro & Sheila Perez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.escoladeltreball.ulisesmap.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;

/**
 * Client
 * Class communicates with multiple servlets.
 * 
 * Each servlet sends and receives specific information. There are 12 servlets.
 * Some information is sent string in JSON format depend on the servlet.
 *
 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class Client extends AsyncTask<String, Integer, String> {
	
	/**
	 * This servlet inserts the users database. Client sends a user in JSON
	 * format and servlet responds if the user has inserted or not. The JSON has 
	 * name, password and email of user.
	 */
	public static final String SERVLET_INSERT_USER = "ServletInsertUser";
	/** 
	 * This servlet check if exist a user or not. Client sends a user in JSON
	 * format and servlet responds if the user exist or not. The JSON has name and 
	 * password of user.
	 */
	public static final String SERVLET_CHECK_USER = "ServletCheckUser";
	/**
	 * This servlet get routes. Client send reference of route and
	 * servlet responds string with format JSONArray. The JSONArray has
	 * name, description, image and average of routes.
	 */
	public static final String SERVLET_ROUTES = "ServletRoutes";
	/**
	 * This servlet get points of routes. Client send name route and servlet
	 * response string with format JSONArray. The JSONArray has name, latitude,
	 * longitude, street, description, url and image of points.
	 */
	public static final String SERVLET_POINTS_OF_ROUTE = "ServletRelationRP";
	/**
	 * This servlet get point. Client send reference of points and
	 * servlet responds string with format JSONArray. The JSONArray has name, latitude,
	 * longitude, street, description, url, and image of points.
	 */
	public static final String SERVLET_POINT = "ServletPoints";
	/**
	 * This servlet get comment and valoration. Client send name of route and
	 * servlet responds string with format JSONArray. The JSONArray has comment and 
	 * valoration and for each comment or valoration has definition and user.
	 */
	public static final String SERVLET_COMMENT_AND_VALORATION = "ServletCommentValoration";
	/** 
	 * This servlet insert the comment database. Client send a comment in JSON format and servlet 
	 * responds if the comment has inserted or not. The JSON has definition, user and route. 
	 */
	public static final String SERVLET_COMMENT_INSERT = "ServletInsertComment";
	/**
	 * This servlet check if exist a comment of user or not. Client send in JSON format and servlet
	 * responds if the comment of user exist or not. The JSON has name of route and name of user.
	 */
	public static final String SERVLET_CHECK_COMMENT = "ServletCheckComment";
	/**
	 * This servlet insert the valoration database. Client send a comment in JSON format and servlet 
	 * responds if the valoration has inserted or not. The JSON has value ranking, user and route. 
	 */
	public static final String SERVLET_VALORATION_INSERT = "ServletInsertValoration";
	/**
	 * This servlet check if exist a valoration of user or not. Client send in JSON format and servlet
	 * responds if the valoration of user exist or not. The JSON has name of route and name of user.
	 */
	public static final String SERVLET_CHECK_VALORATION = "ServletCheckValoration";
	/**
	 * This servlet get all countries database. Client not send nothing and servlet responds string 
	 * with format JSONArray. The JSONArray has name of country.
	 */
	public static final String SERVLET_COUNTRIES = "ServletCities";
	/**
	 * This servlet get all cities of countries database. Client send name of country and servlet responds 
	 * string with format JSONArray. The JSONArray has name of city.
	 */
	public static final String SERVLET_CITIES = "ServletCities";
	
	/**
	 * Answer when the request of servlet has gone well.
	 */
	public static final String TRUE_CHECK = "true";
	/**
	 * Answer when the request of servlet hasn't gone well.
	 */
	public static final String FALSE_CHECK = "false";
	/**
	 * Path of server
	 */
	private static final String URL = "http://ulises-ulisesmap.rhcloud.com/";
	/**
	 * Path of servlet
	 */
	private String myURL;
	/**
	 * Response of servlet
	 */
	private String response;
	/**
	 * Determines whether or not the client sends information
	 */
	private boolean sendMessage;
	
	/**
	 * Constructor
	 * 
	 * @param nameServlet
	 *            name of servlet. Name of servlet is default value for a
	 *            variable published.
	 * @param sendMessage
	 *            true if client has to send a message or false if client hasn't
	 *            to send message
	 */
	public Client(String nameServlet, boolean sendMessage) {
		super();
		this.myURL = URL + nameServlet;
		this.sendMessage = sendMessage;
	}
	
	/**
	 * Connect with servlet, send information and receiver response.
	 */
	protected String doInBackground(String... String) {
		try {
			URL url = new URL(myURL);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF8");
			String message = (sendMessage) ? String[0].toString() : FALSE_CHECK;
			out.write(message);
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));
			response = in.readLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
