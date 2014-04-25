package org.escoladeltreball.ulisesmap.activities;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.escoladeltreball.ulisesmap.R;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
			if (!check_anonymous.isChecked()) {
				EditText editUser = (EditText) findViewById(R.id.edit_login);
				EditText editPwd = (EditText) findViewById(R.id.edit_psw);
				String user = editUser.getText().toString();
				String pwd = editPwd.getText().toString();
				existLogin(user, pwd);				
			}
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
		
	}
	
	private boolean existLogin(String user, String password) {
		return false;
	}
	
	public static JSONObject getJSONfromURL(String url) {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        // Download JSON data from URL
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try {

            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jArray;
    }
}
