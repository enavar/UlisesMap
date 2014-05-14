package org.escoladeltreball.ulisesmap.converter;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.model.City;
import org.escoladeltreball.ulisesmap.model.CommentValoration;
import org.escoladeltreball.ulisesmap.model.Route;
import org.escoladeltreball.ulisesmap.model.Point;
import org.escoladeltreball.ulisesmap.model.Comment;
import org.escoladeltreball.ulisesmap.model.User;
import org.escoladeltreball.ulisesmap.model.Valoration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

public class Converter {
	
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
	
	public static String convertCommentToJSONObject(String nameUser, String nameRoute) {
		JSONObject jsonObjectComment = new JSONObject();
		try {
			jsonObjectComment.put(Comment.FIELD_USER, nameUser);
			jsonObjectComment.put(Comment.FIELD_ROUTE, nameRoute);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObjectComment.toString();
	}
	
	public static String convertValorationToJSONObject(int valoration, String nameUser, String nameRoute) {
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
	
	public static String convertValorationToJSONObject(String nameUser, String nameRoute) {
		JSONObject jsonObjectValoration = new JSONObject();
		try {
			jsonObjectValoration.put(Valoration.FIELD_USER, nameUser);
			jsonObjectValoration.put(Valoration.FIELD_ROUTE, nameRoute);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObjectValoration.toString();
	}
	
	public static ArrayList<Route> convertStringToRoutes(String arrayRoutes) {
		ArrayList<Route> routes = new ArrayList<Route>();
		try {
			JSONArray jSONArrayRoutes = new JSONArray(arrayRoutes);
			int size = jSONArrayRoutes.length();
			for (int i = 0; i < size; i++) {
				JSONObject objRoute = jSONArrayRoutes.getJSONObject(i);
				String name = objRoute.getString(Route.FIELD_NAME);
				String description = objRoute.getString(Route.FIELD_DESCRIPTION);
				float avg = Float.valueOf(objRoute.getString(Route.FIELD_AVG));
				Route route = new Route(name, description, avg);
				routes.add(route);				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return routes;
	}
	
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
				String street = objPoint.getString(Point.FIELD_STREET);
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
	
	public static ArrayList<City> convertStrintToCities(String arrayCities) {
		ArrayList<City> cities = new ArrayList<City>();
		try {
			JSONArray jSONArrayPoints = new JSONArray(arrayCities);
			int size = jSONArrayPoints.length();
			for (int i = 0; i < size; i++) {
				JSONObject objCity = jSONArrayPoints.getJSONObject(i);
				String ref = objCity.getString(City.FIELD_PRIMARY_KEY);
				String name = objCity.getString(City.FIELD_NAME);
				City city = new City(ref, name);
				cities.add(city);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cities;
	}
	
	public static String[] convertStringToCountry(String arrayCountry) {
		String [] countries = null;		
		try {
			JSONArray jSONArrayPoints = new JSONArray(arrayCountry);
			int size = jSONArrayPoints.length();
			countries = new String[size];
			for (int i = 0; i < size; i++) {
				JSONObject objCity = jSONArrayPoints.getJSONObject(i);
				String country = objCity.getString(City.FIELD_COUNTRY);
				countries[i] = country;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return countries;
			
	}
	
	public static ArrayList<CommentValoration> convertStringToCommentsValorations(String arrayComments) {
		ArrayList<CommentValoration> comments = new ArrayList<CommentValoration>();
		try {
			JSONArray jSONArrayPoints = new JSONArray(arrayComments);
			int size = jSONArrayPoints.length();
			for (int i = 0; i < size; i++) {
				JSONObject objComment = jSONArrayPoints.getJSONObject(i);
				String user = objComment.getString("user");
				String comment = objComment.getString("comment");
				if (comment == null) {
					comment = "no comment has been added";
				}
				int valoration = objComment.getInt("valoration");
				CommentValoration commentvaloration = new CommentValoration(valoration,comment,user);
				comments.add(commentvaloration);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return comments;

	}
}
