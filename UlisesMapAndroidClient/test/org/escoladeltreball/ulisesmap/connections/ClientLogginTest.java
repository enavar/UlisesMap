package org.escoladeltreball.ulisesmap.connections;

import junit.framework.Assert;

import org.escoladeltreball.ulisesmap.model.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class ClientLogginTest {
	
	private static final String TRUE = "Welcome!";
	private static final String FALSE = "Try again";
	private ClientLoggin clientCheckUser;
	private JSONObject user01;
	private JSONObject user02;
	private JSONObject user03;
	private JSONObject user04;
	private JSONObject user05;
	private JSONObject user06;
	private JSONObject user07;
	private JSONObject user08;
	private JSONObject user09;
	private JSONObject user10;
	private JSONObject user11;
	private JSONObject user12;
	private JSONObject user13;
	private JSONObject user14;
	private JSONObject user15;
	private JSONObject user16;
	private JSONObject user17;
	private JSONObject user18;
	private JSONObject user19;
	private JSONObject user20;
	private JSONObject userNull;
	
	@Before
	public void setUp() throws JSONException{
		clientCheckUser = new ClientLoggin(ClientLoggin.SERVLET_CHECK_USER);
		user01 = new JSONObject();
		user01.put(User.FIELD_NAME, "admin");
		user01.put(User.FIELD_PSW, "admin");
		user02 = new JSONObject();
		user02.put(User.FIELD_NAME, "castorp");
		user02.put(User.FIELD_PSW, "buscargrial");
		user03 = new JSONObject();
		user03.put(User.FIELD_NAME, "penelope");
		user03.put(User.FIELD_PSW, "araña");
		user04 = new JSONObject();
		user04.put(User.FIELD_NAME, "Heraclit");
		user04.put(User.FIELD_PSW, "fire");
		user05 = new JSONObject();
		user05.put(User.FIELD_NAME, "Parmenides");
		user05.put(User.FIELD_PSW, "aparences");
		user06 = new JSONObject();
		user06.put(User.FIELD_NAME, "atenea");
		user06.put(User.FIELD_PSW, "guerra");
		user07 = new JSONObject();
		user07.put(User.FIELD_NAME, "hera");
		user07.put(User.FIELD_PSW, "familia");
		user08 = new JSONObject();
		user08.put(User.FIELD_NAME, "janeGoodall");
		user08.put(User.FIELD_PSW, "monos");
		user09 = new JSONObject();
		user09.put(User.FIELD_NAME, "Arrietty");
		user09.put(User.FIELD_PSW, "diminuta");
		user10 = new JSONObject();
		user10.put(User.FIELD_NAME, "VladimirNabokob");
		user10.put(User.FIELD_PSW, "ninfulas");
		user11 = new JSONObject();
		user11.put(User.FIELD_NAME, "Mandonguilla");
		user11.put(User.FIELD_PSW, "espaguetis");
		user12 = new JSONObject();
		user12.put(User.FIELD_NAME, "Rosa");
		user12.put(User.FIELD_NAME, "Llibre");
		user13 = new JSONObject();
		user13.put(User.FIELD_NAME, "Robot");
		user13.put(User.FIELD_PSW, "metall");
		user14 = new JSONObject();
		user14.put(User.FIELD_NAME, "cadira");
		user14.put(User.FIELD_PSW, "taula");
		user15 = new JSONObject();
		user15.put(User.FIELD_NAME, "NombrePi");
		user15.put(User.FIELD_PSW, "Irracional");
		user16 = new JSONObject();
		user16.put(User.FIELD_NAME, "gat");
		user16.put(User.FIELD_PSW, "peix");
		user17 = new JSONObject();
		user17.put(User.FIELD_NAME, "gos");
		user17.put(User.FIELD_PSW, "dalmata");
		user18 = new JSONObject();
		user18.put(User.FIELD_NAME, "Carn");
		user18.put(User.FIELD_PSW, "ungle");
		user19 = new JSONObject();
		user19.put(User.FIELD_NAME, "Matematic");
		user19.put(User.FIELD_PSW, "123456");
		user20 = new JSONObject();
		user20.put(User.FIELD_NAME, "coral");
		user20.put(User.FIELD_PSW, "mari");
		userNull = new JSONObject();
		
	}
	
	@Test
	public void testUserExist() {
		JSONObject [] users = new JSONObject[1];
		users[0] = user01;
		clientCheckUser.execute(users);
		String response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
		users[0] = user02;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
		users[0] = user03;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
		users[0] = user04;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
		users[0] = user05;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
		users[0] = user06;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
		users[0] = user07;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
		users[0] = user08;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
		users[0] = user09;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
		users[0] = user10;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(TRUE, response);
	}
	
	@Test
	public void testUserNotExist() {
		JSONObject [] users = new JSONObject[1];
		users[0] = user11;
		clientCheckUser.execute(users);
		String response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
		users[0] = user12;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
		users[0] = user13;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
		users[0] = user14;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
		users[0] = user15;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
		users[0] = user16;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
		users[0] = user17;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
		users[0] = user18;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
		users[0] = user19;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
		users[0] = user20;
		clientCheckUser.execute(users);
		response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
	}
	
	@Test
	public void testUserNull() {
		JSONObject [] users = new JSONObject[1];
		users[0] = userNull;
		clientCheckUser.execute(users);
		String response = clientCheckUser.getResponse();
		Assert.assertEquals(FALSE, response);
	}
}