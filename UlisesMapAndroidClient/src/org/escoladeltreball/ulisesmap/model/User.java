package org.escoladeltreball.ulisesmap.model;

public class User {
	
	public static final String FIELD_NAME = "user";
	public static final String FIELD_MAIL = "mail";
	public static final String FIELD_PSW = "password";	
	
	private String name;
	private String password;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public User(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}	
	
}
