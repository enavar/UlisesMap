package org.escoladeltreball.ulisesmap.activities;

import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	
	EditText editName;
	EditText editMail;
	EditText editPass;
	EditText editRepeatPass;

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

	@Override
	public void onClick(View v) {
		String name = editName.getText().toString();
		String mail = editMail.getText().toString();
		String pass = editPass.getText().toString();
		if (name.equals("") || pass.equals("") || mail.equals("")) {
			Toast.makeText(this, R.string.noName, Toast.LENGTH_SHORT).show();
			return;
		}
		String repeatPass = editRepeatPass.getText().toString();
		if (!repeatPass.equals(pass)) {
			editPass.setText("");
			Toast.makeText(this, R.string.wrongPass, Toast.LENGTH_SHORT).show();
			return;
		}
		send(name,mail,pass);
	}
	
	public boolean send(String name, String mail, String pass) {
		return false;
	}

}
