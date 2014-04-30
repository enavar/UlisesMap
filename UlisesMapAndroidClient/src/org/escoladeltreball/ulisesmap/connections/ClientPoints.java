package org.escoladeltreball.ulisesmap.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class ClientPoints extends AsyncTask<JSONObject, Integer, String>{
	
	private static final String SERVLET_POINTS = "http://wiam2-ulisesmap.rhcloud.com/ServletPoints";
	private String arrayPoints;
	
	public ClientPoints(String nameServlet) {
		super();
	}

	protected String doInBackground(JSONObject... jsonObjects) {
		try {
			URL url = new URL(SERVLET_POINTS);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			arrayPoints=in.readLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayPoints;
	}

	public String getResponse() {
		return arrayPoints;
	}

}
