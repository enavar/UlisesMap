package org.escoladeltreball.ulisesmap.activities;



import org.escoladeltreball.ulisesmap.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {
	
	private Button btn_register;
	private Button btn_enter;
	private CheckBox check_anonymous;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btn_register = (Button) findViewById(R.id.button_register);
		btn_enter = (Button) findViewById(R.id.button_enter);
		check_anonymous = (CheckBox) findViewById(R.id.check_anonymous);
		btn_register.setOnClickListener(this);
		btn_enter.setOnClickListener(this);
		check_anonymous.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view.equals(btn_enter)) {
			/**if (!check_anonymous.isChecked()) {
				EditText editUser = (EditText) findViewById(R.id.edit_login);
				EditText editPwd = (EditText) findViewById(R.id.edit_psw);
				String user = editUser.getText().toString();
				String pwd = editPwd.getText().toString();
				existLogin(user, pwd);				
			}**/
			intentMenuActivity();
		} else if (view.equals(btn_register)) {
			intentRegisterActivity();
		} else {
			checkOptionAnonymous();
		}
	}
	
	private void checkOptionAnonymous() {
		EditText editUser = (EditText) findViewById(R.id.edit_login);
		EditText editPsw = (EditText) findViewById(R.id.edit_psw);
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
		
	}
	
	private void intentMenuActivity() {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);		
	}
	
	private boolean existLogin(String user, String password) {
		return false;
	}
}
