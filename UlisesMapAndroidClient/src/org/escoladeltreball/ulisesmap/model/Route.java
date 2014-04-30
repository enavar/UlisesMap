package org.escoladeltreball.ulisesmap.model;

import java.util.HashSet;

public class Route {
	
	private String name;
	private String description;
	private HashSet<Point> points;
	private HashSet<Comment> comments;
	private HashSet<Valoration> valorations;
	private float valorationAverage;
		
	public Route(String name, String description, float valorationAverage, HashSet<Point> points, HashSet<Comment> comments,
			HashSet<Valoration> valorations) {
		super();
		this.name = name;
		this.description = description;
		this.valorationAverage = valorationAverage;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getValorationAverage() {
		return valorationAverage;
	}
}
