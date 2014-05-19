package org.escoladeltreball.ulisesmap.model;

import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;

public class User {
	
	public static final String FIELD_NAME = "user";
	public static final String FIELD_MAIL = "email";
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
	
	public static boolean existLogin(String nameUser, String password) {
		String response = null;
		try {
			Client client = new Client(Client.SERVLET_CHECK_USER, true);
			String user = Converter.convertUserToJSONObject(nameUser, password);
			response = client.execute(user).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}		
		return response.equals(Client.TRUE_CHECK);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
