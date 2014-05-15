package org.escoladeltreball.ulisesmap.activities;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Settings;
import org.escoladeltreball.ulisesmap.model.User;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {
	
	private Button btnRegister;
	private Button btnEnter;
	private CheckBox checkAnonymous;
	private CheckBox checkRemember;
	private EditText editUser;
	private EditText editPwd;	
	
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

	@Override
	public void onClick(View view) {
		if (view.equals(btnRegister)) {
			intentRegisterActivity();
		} else if(checkAnonymous.isChecked()) {
			progress.show();
			new IntentLauncher().execute();			
		} else {
			String user = editUser.getText().toString();
			String pwd = editPwd.getText().toString();
			if (User.existLogin(user, pwd)) {
				if(checkRemember.isChecked()) {
					Editor editor = prefs.edit();
					editor.putString("userName", user);
					editor.putString("password", pwd);
					editor.commit();
				} 
				Settings.userName = user;
				progress.show();
				new IntentLauncher().execute();	
			} else {
				Toast.makeText(this, R.string.error_not_checked_user, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private class IntentLauncher extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... s) {		
			intentMenuActivity();
			return null;
		}
		
		 @Override
	        protected void onPostExecute(String result) {
			 progress.dismiss();
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
		if (buttonView.equals(checkAnonymous)) {
			checkedAnonymous(isChecked);
		} else {
			checkedRememeber(isChecked);
		}
	}
	
	private void checkedAnonymous(boolean isChecked) {
		editUser.setEnabled(!isChecked);
		editPwd.setEnabled(!isChecked);	
		if (isChecked) {
			checkRemember.setChecked(false);
			editUser.setText("");
			editPwd.setText("");
		} 
	}
	
	private void checkedRememeber(boolean isChecked) {
		if (isChecked) {
			checkAnonymous.setChecked(false);
		}
	}
}
