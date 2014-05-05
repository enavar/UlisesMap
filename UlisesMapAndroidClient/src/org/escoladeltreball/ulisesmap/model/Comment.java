package org.escoladeltreball.ulisesmap.model;

public class Comment {
	
	public static final int POSITION_COMMENT = 0;
	public static final String FIELD_DEFITION = "def";
	public static final String FIELD_ROUTE = "fk_route";
	public static final String FIELD_USER = "fk_user";
	
	private String definition;
	private User user;
	private Route route;
			
	public Comment(String definition, User user, Route route) {
		this.definition = definition;
		this.user = user;
		this.route = route;
	}
	
	public Comment(String definition, User user) {
		this.definition = definition;
		this.user = user;
	}

	public String getDefinition() {
		return definition;
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	public User getUser() {
		return user;
	}
	
	public Route getRoute() {
		return route;
	}
	
	

}
