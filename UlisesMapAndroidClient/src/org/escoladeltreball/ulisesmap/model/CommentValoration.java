package org.escoladeltreball.ulisesmap.model;

public class CommentValoration {
	
	private int val;
	private String com;
	private String user;
	
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public CommentValoration(int val, String com, String user) {
		this.val = val;
		this.com = com;
		this.user = user;
	}	
	
	public CommentValoration() {
		
	}
	

}
