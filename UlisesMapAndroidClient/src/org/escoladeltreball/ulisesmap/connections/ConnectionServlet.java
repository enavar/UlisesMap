package org.escoladeltreball.ulisesmap.connections;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

public class ConnectionServlet {
	
	public static final String USERS_SERVLET = "UsersServlet";
	public static final String ROUTES_SERVLET = "RoutesServlet";
	public static final String POINTS_SERVLET = "PointsServlet";
	private static final String URL = "http://wiam2-ulisesmap.rhcloud.com/";
	private HttpPost post;
	private HttpResponse response;
	private HttpClient client;
	
	public ConnectionServlet(String nameServlet) {
		client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        post = new HttpPost(URL + nameServlet);
	}
	
	public HttpResponse sendJSONObject(JSONObject jsonObject) throws ClientProtocolException, IOException {
		StringEntity entity = new StringEntity(jsonObject.toString());
		entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		post.setEntity(entity);
		response = client.execute(post);
		return response;
	}
	
	public void setServlet(String nameServlet) {
		post = new HttpPost(URL + nameServlet);
	}
}
