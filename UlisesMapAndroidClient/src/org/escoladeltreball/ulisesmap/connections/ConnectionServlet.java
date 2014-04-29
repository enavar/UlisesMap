package org.escoladeltreball.ulisesmap.connections;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.AsyncTask;

public class ConnectionServlet extends AsyncTask<JSONObject, Integer, HttpResponse> {
	
	public static final String USERS_SERVLET = "UsersServlet";
	public static final String ROUTES_SERVLET = "RoutesServlet";
	public static final String POINTS_SERVLET = "PointsServlet";
	private static final String URL = "http://wiam2-ulisesmap.rhcloud.com/";
	private final HttpClient client = new DefaultHttpClient();
	private String myURL;
	private HttpPost post;
	private HttpResponse response;
	
	public ConnectionServlet(String nameServlet) {
		super();
		this.myURL = URL + nameServlet;
	}

	protected HttpResponse doInBackground(JSONObject... jsonObjects) {
		try {
			post = new HttpPost(myURL);
			StringEntity entity = new StringEntity(jsonObjects[0].toString());
			post.setHeader(HTTP.CONTENT_TYPE,"application/json");
			post.setEntity(entity);
			System.out.println("Entity " + entity.toString());
			System.out.println("post " + post.toString());
			response = client.execute(post);
			System.out.println(response.toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
