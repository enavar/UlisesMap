package org.escoladeltreball.ulisesmap.activities;




import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
	
	private Button btn_register;
	private Button btn_enter;
	private CheckBox check_anonymous;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btn_register = (Button) findViewById(R.id.buttonRegister);
		btn_enter = (Button) findViewById(R.id.buttonEnter);
		check_anonymous = (CheckBox) findViewById(R.id.checkAnonymous);
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
	
	private boolean existLogin(String nameUser, String password) {
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
