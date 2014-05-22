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

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Settings;
import org.escoladeltreball.ulisesmap.model.User;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * LoginActivity Activity that enables the user to log on. Also, since this
 * activity, the user can go RegisterActivity and go in MenuActivity as
 * anonymous user.
 * 
 * @Author: Oleksander Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class LoginActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {

	/** Button of register */
	private Button btnRegister;
	/** Button of enter **/
	private Button btnEnter;
	/** CheckBox of option anonymous */
	private CheckBox checkAnonymous;
	/** CheckBox of option remember login */
	private CheckBox checkRemember;
	/** EditText of name user */
	private EditText editUser;
	/** EditText of password */
	private EditText editPwd;

	/**
	 * Create LoginActivity. Add listener of buttons and checkbox and Fill the
	 * login fields if the user has previously entered the remember option.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnRegister = (Button) findViewById(R.id.buttonRegister);
		btnEnter = (Button) findViewById(R.id.buttonEnter);
		checkAnonymous = (CheckBox) findViewById(R.id.checkAnonymous);
		checkRemember = (CheckBox) findViewById(R.id.checkRemember);
		checkRemember.setChecked(true);
		editUser = (EditText) findViewById(R.id.edit_login);
		editPwd = (EditText) findViewById(R.id.editPsw);
		btnRegister.setOnClickListener(this);
		btnEnter.setOnClickListener(this);
		checkAnonymous.setOnCheckedChangeListener(this);
		checkRemember.setOnCheckedChangeListener(this);
		if (prefs.contains("userName")) {
			editUser.setText(prefs.getString("userName", ""));
			editPwd.setText(prefs.getString("password", ""));
		}
	}

	/**
	 * Method is called by the listener when the user clicks. If user click in
	 * button register, when it go to Register activity. If user click in button
	 * enter, when it go to Menu activity. Before going to activity menu, check
	 * the login is correct or if the anonymous user option. If there is a
	 * problem, it displays messages to the user.
	 */
	@Override
	public void onClick(View view) {
		if (view.equals(btnRegister)) {
			intentRegisterActivity();
		} else if (checkAnonymous.isChecked()) {
			progress.show();
			intentMenuActivity();
		} else {
			String user = editUser.getText().toString();
			String pwd = editPwd.getText().toString();
			if (User.existLogin(user, pwd)) {
				if (checkRemember.isChecked()) {
					Editor editor = prefs.edit();
					editor.putString("userName", user);
					editor.putString("password", pwd);
					editor.commit();
				}
				Settings.userName = user;
				progress.show();
				intentMenuActivity();
			} else {
				Toast.makeText(this, R.string.error_not_checked_user,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * Launch RegisterActivity
	 */
	private void intentRegisterActivity() {
		Intent intent = new Intent(this, RegisterActivity.class);
		new IntentLauncher().execute(intent);
	}
	
	/**
	 * Launch MenuActivity
	 */
	private void intentMenuActivity() {
		Intent intent = new Intent(this, MenuActivity.class);
		new IntentLauncher().execute(intent);		
	}
	
	/**
	 * Method is called by the listener when the user 
	 * click to checkbox.
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.equals(checkAnonymous)) {
			checkedAnonymous(isChecked);
		} else {
			checkedRememeber(isChecked);
		}
	}
	
	/**
	 * If user click option Anonymous, then checked others options.
	 * If option anonymous is enable, then disable option remember
	 * and editText of login.
	 * If option anonymous is disable, then enable editText of login. 
	 * @param isChecked state of checkbox
	 */
	private void checkedAnonymous(boolean isChecked) {
		editUser.setEnabled(!isChecked);
		editPwd.setEnabled(!isChecked);
		if (isChecked) {
			checkRemember.setChecked(false);
			editUser.setText("");
			editPwd.setText("");
		}
	}
	
	/**
	 * If user click option remember, then checked others options.
	 * If option remember is enable, then disable option anonymous. 
	 * @param isChecked state of checkbox
	 */
	private void checkedRememeber(boolean isChecked) {
		if (isChecked) {
			checkAnonymous.setChecked(false);
		}
	}
}
