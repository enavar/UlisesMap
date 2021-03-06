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
package org.escoladeltreball.ulisesmap.converter;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.model.City;
import org.escoladeltreball.ulisesmap.model.Comment;
import org.escoladeltreball.ulisesmap.model.CommentValoration;
import org.escoladeltreball.ulisesmap.model.Point;
import org.escoladeltreball.ulisesmap.model.Route;
import org.escoladeltreball.ulisesmap.model.User;
import org.escoladeltreball.ulisesmap.model.Valoration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

/**
 * Converter
 * Client helper Class. Converts the information the client sends to JSON String and
 * converts the information the servlet to object specific.
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class Converter {
	
	private static final String PATH = "_";
	private static final String SPACE = " ";
	
	/**
	 * Convert the information of user in JSON to String because client 
	 * can send to servlet
	 * 
	 * @param name name of user
	 * @param password password of user
	 * @return user in format JSON to String.
	 */
	public static String convertUserToJSONObject(String name, String password) {
		JSONObject jsonObjectUser = new JSONObject();
		try {			
			jsonObjectUser.put(User.FIELD_NAME, name);
			jsonObjectUser.put(User.FIELD_PSW, password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObjectUser.toString();
	}
	
	/**
	 * Convert the information of user in JSON to String because client 
	 * can send to servlet
	 * 
	 * @param name name of user
	 * @param password password of user
	 * @param email email of user
	 * @return user in format JSON to String.
	 */
	public static String convertUserToJSONObject(String name, String password, String email) {
		JSONObject jsonObjectUser = new JSONObject();
		try {			
			jsonObjectUser.put(User.FIELD_NAME, name);
			jsonObjectUser.put(User.FIELD_PSW, password);
			jsonObjectUser.put(User.FIELD_MAIL, email);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObjectUser.toString();
	}
	
	/**
	 * Convert the information of comment in JSON to String because
	 * client can send to serv(comment == null)let
	 * 
	 * @param definition definition of comment
	 * @param nameUser user of comment
	 * @param nameRoute route of comment
	 * @return comment in format JSON to String.
	 */
	public static String convertCommentToJSONObject(String definition, String nameUser, String nameRoute) {
		JSONObject jsonObjectComment = new JSONObject();
		try {			
			jsonObjectComment.put(Comment.FIELD_DEFITION, definition);
			jsonObjectComment.put(Comment.FIELD_USER, nameUser);
			jsonObjectComment.put(Comment.FIELD_ROUTE, nameRoute);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObjectComment.toString();
	}
	
	/**
	 * Convert the information of valoration in JSON to String because 
	 * client can send to servlet
	 * 
	 * @param valoration value ranking of valoration
	 * @param nameUser user of valoration
	 * @param nameRoute route of valoration
	 * @return valoration in format JSON to String.
	 */
	public static String convertValorationToJSONObject(double valoration, String nameUser, String nameRoute) {
		JSONObject jsonObjectValoration = new JSONObject();
		try {
			jsonObjectValoration.put(Valoration.FIELD_VALORATION, valoration);
			jsonObjectValoration.put(Valoration.FIELD_USER, nameUser);
			jsonObjectValoration.put(Valoration.FIELD_ROUTE, nameRoute);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObjectValoration.toString();
	}
	
	/**
	 * Convert the information of valoration in JSON to String because
	 * client can send to servlet
	 * 
	 * @param nameUser user of valoration
	 * @param nameRoute route of valoration
	 * @return valoration in format JSON to String
	 */
	public static String convertCheckValoratingToJSONObject(String nameUser, String nameRoute) {
		JSONObject jsonObjectValoration = new JSONObject();
		try {
			jsonObjectValoration.put(Valoration.FIELD_USER, nameUser);
			jsonObjectValoration.put(Valoration.FIELD_ROUTE, nameRoute);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObjectValoration.toString();
	}
	
	/**
	 * Convert the JSONArray to String in routes
	 * 
	 * @param arrayRoutes JSONArray to String with information of routes
	 * @return list of routes
	 */
	public static ArrayList<Route> convertStringToRoutes(String arrayRoutes) {
		ArrayList<Route> routes = new ArrayList<Route>();
		try {
			JSONArray jSONArrayRoutes = new JSONArray(arrayRoutes);
			int size = jSONArrayRoutes.length();
			for (int i = 0; i < size; i++) {
				JSONObject objRoute = jSONArrayRoutes.getJSONObject(i);
				String name = objRoute.getString(Route.FIELD_NAME);
				String description = objRoute.getString(Route.FIELD_DESCRIPTION);
				double avg = objRoute.getDouble(Route.FIELD_AVG);
				String image = objRoute.getString(Route.FIELD_IMAGE);
				
				Route route = new Route(name, description, avg, image);
				routes.add(route);				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return routes;
	}
	
	/**
	 * Convert the JSONArray to String in points
	 * 
	 * @param arrayPoints JSONArray to String with information of points
	 * @return list of points
	 */
	public static ArrayList<Point> convertStringToPoints(String arrayPoints) {
		ArrayList<Point> points = new ArrayList<Point>();
		try {
			JSONArray jSONArrayPoints = new JSONArray(arrayPoints);
			int size = jSONArrayPoints.length();
			for (int i = 0; i < size; i++) {
				JSONObject objPoint = jSONArrayPoints.getJSONObject(i);
				String name = objPoint.getString(Point.FIELD_NAME);
				double lat = objPoint.getDouble(Point.FIELD_LAT);
				double lon = objPoint.getDouble(Point.FIELD_LON);
				String street = (!objPoint.isNull(Point.FIELD_STREET)) ? objPoint.getString(Point.FIELD_STREET) : "";
				String description = objPoint.getString(Point.FIELD_DESCRIPTION);
				String url = (!objPoint.isNull(Point.FIELD_URL)) ? objPoint.getString(Point.FIELD_URL) : "";
				String image = objPoint.getString(Point.FIELD_IMAGE);
				GeoPoint geoPoint = new GeoPoint(lat, lon);
				Point point = new Point(name, geoPoint, street, description, image, url);
				points.add(point);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return points;
	}
	
	/**
	 * Convert the JSONArray to String in points
	 * 
	 * @param arrayPoints JSONArray to String with information of points
	 * @return list of points
	 */
	public static ArrayList<Point> convertStringToPointsOfRoutes(String arrayPoints) {
		ArrayList<Point> points = new ArrayList<Point>();
		try {
			JSONArray jSONArrayPoints = new JSONArray(arrayPoints);
			int size = jSONArrayPoints.length();
			for (int i = 0; i < size; i++) {
				JSONObject objPoint = jSONArrayPoints.getJSONObject(i);
				double lat = objPoint.getDouble(Point.FIELD_LAT);
				double lon = objPoint.getDouble(Point.FIELD_LON);
				GeoPoint geoPoint = new GeoPoint(lat, lon);
				Point point = new Point(geoPoint);
				points.add(point);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return points;		
	}
	
	/**
	 * Convert the JSONArray to String in comments
	 * 
	 * @param arrayComments JSONArray to String witvalorationh information of comments
	 * @return list of comments
	 */
	public static ArrayList<Comment> convertStringToComments(String arrayComments) {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		try {
			JSONArray jsonArray = new JSONArray(arrayComments);
			JSONArray jSONArrayComments = (JSONArray) jsonArray.get(Comment.POSITION_COMMENT);
			int size = jSONArrayComments.length();
			for (int i = 0; i < size; i++) {
				JSONObject objComment = jSONArrayComments.getJSONObject(i);
				String definition = objComment.getString(Comment.FIELD_DEFITION);
				String name = objComment.getString(User.FIELD_NAME);
				User user = new User(name);
				Comment comment = new Comment(definition, user);
				comments.add(comment);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	/**
	 * Convert the JSONArray to String in valorations
	 * 
	 * @param arrayValorations JSONArray to String with information of valorations
	 * @return list of valorations
	 */
	public static ArrayList<Valoration> convertStringToValorations(String arrayValorations) {
		ArrayList<Valoration> valorations = new ArrayList<Valoration>();
		try {
			JSONArray jsonArray = new JSONArray(arrayValorations);
			JSONArray jSONArrayValorations = (JSONArray) jsonArray.get(Valoration.POSITION_VALORATION);
			int size = jSONArrayValorations.length();
			for (int i = 0; i < size; i++) {
				JSONObject objValoration = jSONArrayValorations.getJSONObject(i);
				int valor = objValoration.getInt(Valoration.FIELD_VALORATION);
				String name = objValoration.getString(User.FIELD_NAME);
				User user = new User(name);
				Valoration valoration = new Valoration(valor, user);
				valorations.add(valoration);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return valorations;
	}
	
	/**
	 * Convert the JSONArray to String in cities
	 * 
	 * @param arrayCities JSONArray to String with information of cities
	 * @return list of cities.
	 */
	public static ArrayList<City> convertStrintToCities(String arrayCities) {
		ArrayList<City> cities = new ArrayList<City>();
		try {
			JSONArray jSONArrayPoints = new JSONArray(arrayCities);
			int size = jSONArrayPoints.length();
			for (int i = 0; i < size; i++) {
				JSONObject objCity = jSONArrayPoints.getJSONObject(i);
				String ref = objCity.getString(City.FIELD_PRIMARY_KEY);
				String name = objCity.getString(City.FIELD_NAME);
				name = convertBarToSpace(name);
				City city = new City(ref, name);
				cities.add(city);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cities;
	}
	
	/**
	 * Convert the JSONArray to String in countries
	 * 
	 * @param arrayCountry JSONArray to String with information of countries
	 * @return list of countries
	 */
	public static String[] convertStringToCountry(String arrayCountry) {
		String [] countries = null;		
		try {
			JSONArray jSONArrayPoints = new JSONArray(arrayCountry);
			int size = jSONArrayPoints.length();
			countries = new String[size];
			for (int i = 0; i < size; i++) {
				JSONObject objCity = jSONArrayPoints.getJSONObject(i);
				String country = objCity.getString(City.FIELD_COUNTRY);
				country = convertBarToSpace(country);
				countries[i] = country;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return countries;
	}
	
	/**
	 * Convert the JSONArray to String in Comments and valorations
	 * 
	 * @param arrayComments JSONArray to String with information of comments and valorations
	 * @return list of comment and valorations
	 */
	public static ArrayList<CommentValoration> convertStringToCommentsValorations(String arrayComments) {
		ArrayList<CommentValoration> comments = new ArrayList<CommentValoration>();
		try {
			JSONArray jSONArrayPoints = new JSONArray(arrayComments);
			int size = jSONArrayPoints.length();
			for (int i = 0; i < size; i++) {
				JSONObject objComment = jSONArrayPoints.getJSONObject(i);
				String user = objComment.getString(CommentValoration.FIELD_USER);
				String comment = objComment.getString(CommentValoration.FIELD_COMMENT);
				if (comment == null) {
					comment = " ";
				}
				int valoration = objComment.getInt(CommentValoration.FIELD_VALUE);
				CommentValoration commentvaloration = new CommentValoration(valoration,comment,user);
				comments.add(commentvaloration);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	/**
	 * Replace space of path
	 * 
	 * @param name name of city or country
	 * @return name of city or country with path
	 */
	public static String convertSpaceToBar(String name) {
		return name.replace(SPACE, PATH);
	}
	
	/**
	 * Replace path of space
	 * 
	 * @param name name of city or country
	 * @return name of city or country with space
	 */
	public static String convertBarToSpace(String name) {
		return name.replace(PATH, SPACE);
	}
}
