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
	public void test() throws SQLException, ClassNotFoundException {
		Users u = new Users();
		u.connect();
		String userName = "admin";
		String pass = "admin";
		u.insertUser(userName,pass);	
		boolean result = u.selectUserByName(userName,pass);
		assertTrue(result);
	}

}
