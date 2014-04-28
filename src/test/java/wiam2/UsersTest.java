package wiam2;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import ulisesServlet.UsersServlet;
import wiamDB.Users;

/*
 * UsersTest    
 *
 * Copyright 2014 local 
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

public class UsersTest {

	@Test
	public void test() throws SQLException {
		UsersServlet us = new UsersServlet();
		Users u = new Users();
		u.connect(us.con);
		String userName = "admin";
		String pass = "admin";
		u.insertUser(userName,pass);	
		boolean result = u.selectUserByName(userName,pass);
		assertTrue(result);
	}

}
