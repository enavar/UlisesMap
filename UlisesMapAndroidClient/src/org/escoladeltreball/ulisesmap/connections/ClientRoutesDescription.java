package org.escoladeltreball.ulisesmap.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;

public class ClientRoutesDescription extends AsyncTask<String, Integer, String> {
	
	private static String URL = "http://ulises-ulisesmap.rhcloud.com/ServletRoutesDescription";
	private String dictionary;
	
	@Override
	protected String doInBackground(String... idRoute) {
		try {
			URL url = new URL(URL);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(idRoute[0].toString());
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			dictionary=in.readLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dictionary;
	}
	
	public String getResponse() {
		return dictionary;
	}

}
