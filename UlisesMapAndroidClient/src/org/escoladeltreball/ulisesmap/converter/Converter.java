package org.escoladeltreball.ulisesmap.converter;

import java.util.ArrayList;

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
				String url = objPoint.getString(Point.FIELD_URL);
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
	
	public static ArrayList<Valoration> convertStringToValoration(String arrayValorations) {
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
}