package org.escoladeltreball.ulisesmap.activities;


import org.escoladeltreball.ulisesmap.R;
<<<<<<< HEAD
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.User;
=======
>>>>>>> branch 'master' of https://github.com/enavar/UlisesMap.git

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
			editRepeatPass.setText("");
			Toast.makeText(this, R.string.wrongPass, Toast.LENGTH_SHORT).show();
			return;
		}
		if (User.existLogin(name, pass)) {
			editName.setText("");
			editPass.setText("");
			editRepeatPass.setText("");
			Toast.makeText(this, R.string.userExist, Toast.LENGTH_SHORT).show();
			return;
		}
		if (send(name,mail,pass)) {
			Intent intent = new Intent(this, MenuActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(this, R.string.errorSendingData, Toast.LENGTH_SHORT).show();
			return;
		}
	}
	
	public boolean send(String name, String mail, String pass) {
		String response = null;
		try {
			Client client = new Client(Client.SERVLET_INSERT_USER, true);
			String user = Converter.convertUserToJSONObject(name, pass, mail);
			Log.d("user", user);
			response = client.execute(user).get();
			Log.d("response", response);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}		
		return response.equals(Client.TRUE_CHECK);
	}

}
