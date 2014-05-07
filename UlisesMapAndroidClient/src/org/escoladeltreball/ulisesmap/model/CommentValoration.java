package org.escoladeltreball.ulisesmap.model;

public class CommentValoration {
	
	private Valoration val;
	private Comment com;
	private User user;
	
	public CommentValoration(User user) {
		this.user = user;
	}
	
	
	public int getValInt() {
		return val.getValoration();
	}
	public void setValInt(int value) {
		val.setValoration(value);
	}
	public String getCom() {
		return com.getDefinition();
	}
	public void setCom(String comment) {
		com.setDefinition(comment);
	}
	public String getUserName() {
		return user.getName();
	}
	
	
	

}
