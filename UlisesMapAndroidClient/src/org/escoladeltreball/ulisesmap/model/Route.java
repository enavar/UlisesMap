package org.escoladeltreball.ulisesmap.model;

import java.util.HashSet;

public class Route {
	
	private HashSet<Point> points;
	private HashSet<Comment> comments;
	private HashSet<Valoration> valorations;
	
	public Route(HashSet<Point> points, HashSet<Comment> comments,
			HashSet<Valoration> valorations) {
		super();
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

}
