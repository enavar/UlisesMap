package org.escoladeltreball.ulisesmap.model;

import java.io.Serializable;
import java.util.HashSet;

@SuppressWarnings("serial")
public class Route implements Serializable {
	
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_AVG = "avg";
	public static final String FIELD_IMAGE = "image";
	public static final String FIELD_LIST = "routes";
	
	private String name;
	private String description;
	private String image;
	private HashSet<Point> points;
	private HashSet<Comment> comments;
	private HashSet<Valoration> valorations;
	private double valorationAverage;
	private boolean selected;
		
	public Route(String name, String description, double valorationAverage, HashSet<Point> points, HashSet<Comment> comments,
			HashSet<Valoration> valorations) {
		this.name = name;
		this.description = description;
		this.valorationAverage = valorationAverage;
		this.points = points;
		this.comments = comments;
		this.valorations = valorations;
		this.selected = false;
	}
	
	public Route(String name, String description, double valorationAverage, String image) {
		this.name = name;
		this.description = description;
		this.valorationAverage = valorationAverage;
		this.image = image;
		this.selected = false;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public double getValorationAverage() {
		return valorationAverage;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isSelected() {
		return selected;
	}
}
