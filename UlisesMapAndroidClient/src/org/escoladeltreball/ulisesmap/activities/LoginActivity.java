package org.escoladeltreball.ulisesmap.activities;

import org.escoladeltreball.ulisesmap.BaseActivity;
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

public class LoginActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {
	
	private Button btn_register;
	private Button btn_enter;
	private CheckBox check_anonymous;
	private CheckBox check_remember;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btn_register = (Button) findViewById(R.id.buttonRegister);
		btn_enter = (Button) findViewById(R.id.buttonEnter);
		check_anonymous = (CheckBox) findViewById(R.id.checkAnonymous);
		check_remember = (CheckBox) findViewById(R.id.checkRemember);
		btn_register.setOnClickListener(this);
		btn_enter.setOnClickListener(this);
		check_anonymous.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view.equals(btn_register)) {
			intentRegisterActivity();
		} else if(check_anonymous.isChecked()) {
			intentMenuActivity();
		} else {
			EditText editUser = (EditText) findViewById(R.id.edit_login);
			EditText editPwd = (EditText) findViewById(R.id.editPsw);
			String user = editUser.getText().toString();
			String pwd = editPwd.getText().toString();
			if (User.existLogin(user, pwd)) {
				if(check_remember.isChecked()) {
					Editor editor = prefs.edit();
					editor.putString("userName", user);
					editor.putString("password", pwd);
					editor.commit();
				} 
				Settings.userName = user;
				intentMenuActivity();
			}
		}
	}
	private void intentRegisterActivity() {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);		
	}
	
	private void intentMenuActivity() {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		EditText editUser = (EditText) findViewById(R.id.edit_login);
		EditText editPsw = (EditText) findViewById(R.id.editPsw);
		if (isChecked) {
			editUser.setEnabled(false);
			editUser.setFocusable(false);
			editPsw.setEnabled(false);
			editPsw.setFocusable(false);
		} else {
			editUser.setEnabled(true);
			editUser.setFocusable(true);
			editPsw.setEnabled(true);
		}
	}
}
