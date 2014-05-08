package org.escoladeltreball.ulisesmap.model;

public class City {
	
	public static final String FIELD_NAME = "name";
	public static final String FIELD_COUNTRY = "country";
	public static final String FIELD_PRIMARY_KEY = "ref";
	
	private String ref;
	private String name;
	private String country;
	
	public City(String ref, String name, String country) {
		this.ref = ref;
		this.name = name;
		this.country = country;
	}
	
	public City(String ref, String name) {
		this.ref = ref;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRef() {
		return ref;
	}	
}
