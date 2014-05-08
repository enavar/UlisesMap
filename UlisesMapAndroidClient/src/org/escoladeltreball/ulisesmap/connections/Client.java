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

public class Client extends AsyncTask<JSONObject, Integer, String> {
	
	public static final String SERVLET_INSERT_USER = "ServletUserInsert";
	public static final String SERVLET_CHECK_USER = "ServletCheckUser";
	public static final String SERVLET_ROUTES = "ServletRoutes";
	public static final String SERVLET_POINTS_OF_ROUTE = "ServletRelationRP";
	public static final String SERVLET_POINT = "ServletPoints";
	public static final String SERVLET_COMMENT_AND_VALORATION = "ServletCommentValoration";
	public static final String SERVLET_COMMENT_INSERT = "ServletInsertComment";
	public static final String SERVLET_CHECK_COMMENT = "ServletCheckComment";
	public static final String SERVLET_VALORATION_INSERT = "ServletInserValoration";
	public static final String SERVLET_CHECK_VALORATION = "ServletCheckValoration";
	public static final String SERVLET_CITIES = "ServletCities";
	
	public static final String TRUE_CHECK = "true";
	public static final String FALSE_CHECK = "false";
	
	private static final String URL = "http://ulises-ulisesmap.rhcloud.com/";
	
	private String myURL;
	private String response;
	private boolean sendMessage;
	
	public Client(String nameServlet) {
		super();
		this.myURL = URL + nameServlet;
		this.sendMessage = !(nameServlet.equals(SERVLET_CITIES)); 
	}
	
	protected String doInBackground(JSONObject... jsonObjects) {
		try {
			URL url = new URL(myURL);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			String message = (sendMessage) ? jsonObjects[0].toString() : "";
			out.write(message);
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
}
