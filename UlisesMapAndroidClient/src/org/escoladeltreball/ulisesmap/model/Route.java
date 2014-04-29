package org.escoladeltreball.ulisesmap.model;

import java.util.HashSet;

public class Route {
	
	private String name;
	private HashSet<Point> points;
	private HashSet<Comment> comments;
	private HashSet<Valoration> valorations;
	
	public Route(String name, HashSet<Point> points, HashSet<Comment> comments,
			HashSet<Valoration> valorations) {
		super();
		this.name = name;
		this.points = points;
		this.comments = comments;
		this.valorations = valorations;
	}

	public HashSet<Point> getPoints() {
		return points;
	}

	public void setPoints(HashSet<Point> points) {
		this.points = points;
	}

	public HashSet<Comment> getComments() {
		return comments;
	}

	public void setComments(HashSet<Comment> comments) {
		this.comments = comments;
	}

	public HashSet<Valoration> getValorations() {
		return valorations;
	}

	public void setValorations(HashSet<Valoration> valorations) {
		this.valorations = valorations;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getNamesPoints() {
		String[] namesPoints= new String[totalPoints()];
		int i = 0;
		for (Point point : points) {
			namesPoints[i] = point.getName();
			i++;
		}
		return namesPoints;
	}
	
	public int totalPoints() {
		return points.size();
	}
	
	public float valorationAverage() {
		float points = 0;
		for (Valoration valoration : valorations) {
			points += valoration.getValoration(); 
		}
		float pointsAverage = points / valorations.size(); 
		return pointsAverage;
	}
}
