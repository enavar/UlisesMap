package org.escoladeltreball.ulisesmap.activities;




import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.connections.ClientLoggin;
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
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	
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
		check_anonymous.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view.equals(btn_enter)) {
			if (!check_anonymous.isChecked()) {
				EditText editUser = (EditText) findViewById(R.id.edit_login);
				EditText editPwd = (EditText) findViewById(R.id.editPsw);
				String user = editUser.getText().toString();
				String pwd = editPwd.getText().toString();
				if (existLogin(user, pwd)) {
					intentMenuActivity();
				} else {
					Toast.makeText(this, ClientLoggin.FALSE_CHECK_USER, Toast.LENGTH_SHORT).show();
				}
				
			} else {
			 intentMenuActivity();
			}
		} else if (view.equals(btn_register)) {
			intentRegisterActivity();
		} else {
			checkOptionAnonymous();
		}
	}
	
	private void checkOptionAnonymous() {
		EditText editUser = (EditText) findViewById(R.id.edit_login);
		EditText editPsw = (EditText) findViewById(R.id.editPsw);
		if (check_anonymous.isChecked()) {
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
			ClientLoggin servlet = new ClientLoggin(ClientLoggin.SERVLET_CHECK_USER);
			JSONObject [] user = {new JSONObject()};
			user[0].put(User.FIELD_NAME, nameUser);
			user[0].put(User.FIELD_PSW, password);
			response = servlet.execute(user).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return response.equals(ClientLoggin.TRUE_CHECK_USER);
	}
}
