/**
 * Copyright (c) 2014, Oleksander Dovbysh & Elisabet Navarro & Sheila Perez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.escoladeltreball.ulisesmap.activities;


import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * RegisterActivity 
 * Activity that register new user in database
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class RegisterActivity extends Activity implements OnClickListener {
	
	/** EditText of user's name */
	private EditText editName;
	/** EditText of user's mail */
	private EditText editMail;
	/** EditText of user's password */
	private EditText editPass;
	/** EditText of repeat user's password */
	private EditText editRepeatPass;
	
	/**
	 * Create RegisterActivity. Add listener of buttons and checkbox and Fill the
	 * login fields if the user has previously entered the remember option.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);		
		editName = (EditText) findViewById(R.id.userRegister);
		editMail = (EditText) findViewById(R.id.mailRegister);
		editPass = (EditText) findViewById(R.id.pwdRegister1);
		editRepeatPass = (EditText) findViewById(R.id.pwdRegister2);		
		Button btn = (Button) findViewById(R.id.registerAdd);
		btn.setOnClickListener(this);
	}
	
	/**
	 * Method is called by the listener when the user click to button.
	 * Insert new user. 
	 * Before inserting a new user checks the data.
	 */
	@Override
	public void onClick(View v) {
		String name = editName.getText().toString();
		String mail = editMail.getText().toString();
		String pass = editPass.getText().toString();		
		if (checkData(name, mail, pass)) {
			if (send(name,mail,pass)) {
				Intent intent = new Intent(this, MenuActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, R.string.errorSendingData, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * Insert new user to database
	 *  
	 * @param name name of user
	 * @param mail mail of user
	 * @param pass password of user
	 * @return true if user insert correct or false if user not insert.
	 */
	private boolean send(String name, String mail, String pass) {
		String response = null;
		try {
			Client client = new Client(Client.SERVLET_INSERT_USER, true);
			String user = Converter.convertUserToJSONObject(name, pass, mail);
			response = client.execute(user).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}		
		return response.equals(Client.TRUE_CHECK);
	}
	
	/**
	 * Checked data of user.
	 * Check that the fields are empty no. 
	 * Check that the password written like this twice.
	 * Check that user not exist in database
	 * 
	 * @param name name of user
	 * @param mail mail of user
	 * @param pass password of user
	 * @return true if data is correct or false isn't correct.
	 */
	private boolean checkData(String name, String mail, String pass) {
		if (name.equals("") || pass.equals("") || mail.equals("")) {
			Toast.makeText(this, R.string.noName, Toast.LENGTH_SHORT).show();
			return false;
		}
		String repeatPass = editRepeatPass.getText().toString();
		if (!repeatPass.equals(pass)) {
			editPass.setText("");
			editRepeatPass.setText("");
			Toast.makeText(this, R.string.wrongPass, Toast.LENGTH_SHORT).show();
			return false;
		}
		if (User.existLogin(name, pass)) {
			editName.setText("");
			editPass.setText("");
			editRepeatPass.setText("");
			Toast.makeText(this, R.string.userExist, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

}
