package org.escoladeltreball.ulisesmap.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.URL;
import java.net.URLConnection;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class ClientLoggin extends AsyncTask<JSONObject, Integer, String> {
	
	public static final String SERVLET_USER_INSERT = "ServletUserInsert";
	public static final String SERVLET_CHECK_USER = "ServletCheckUser";
	private static final String URL = "http://ulises-ulisesmap.rhcloud.com/";
	private String myURL;
	private String response;
	
	public ClientLoggin(String nameServlet) {
		super();
		this.myURL = URL + nameServlet;
	}

	protected String doInBackground(JSONObject... jsonObjects) {
		try {
			URL url = new URL(myURL);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(jsonObjects[0].toString());
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			response = in.readLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public String getResponse() {
		return response;
	}
}
