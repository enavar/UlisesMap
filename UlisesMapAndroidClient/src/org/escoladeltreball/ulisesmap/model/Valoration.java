package org.escoladeltreball.ulisesmap.model;

public class Valoration {
	
	private int valoration;
	private User user;
	private Route route;
	
	public Valoration(int valoration, User user, Route route) {
		super();
		this.valoration = valoration;
		this.user = user;
		this.route = route;
	}

	public int getValoration() {
		return valoration;
	}
	
	public void setValoration(int valoration) {
		this.valoration = valoration;
	}
	
	public User getUser() {
		return user;
	}
	
	public Route getRoute() {
		return route;
	}

}
