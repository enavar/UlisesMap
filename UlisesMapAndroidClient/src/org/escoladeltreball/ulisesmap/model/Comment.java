package org.escoladeltreball.ulisesmap.model;

public class Comment {
	
	private String text;
	private User user;
	private Route route;
			
	public Comment(String text, User user, Route route) {
		super();
		this.text = text;
		this.user = user;
		this.route = route;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public User getUser() {
		return user;
	}
	
	public Route getRoute() {
		return route;
	}
	
	

}
