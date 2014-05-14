package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.BaseActivity;
import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowCommentsAdapter;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.CommentValoration;
import org.escoladeltreball.ulisesmap.model.Settings;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

public class ShowCommentsActivity extends BaseActivity {
	
	private ArrayList<CommentValoration> comments;
	private JSONObject insertComment = new JSONObject();
	private JSONObject insertValoration = new JSONObject();
	private String userName;
	private String routeName; 
	private boolean disableComment = false;
	private boolean disableValoration = false;
	private EditText et;
	private RatingBar rb;
	private String out;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcomments);
		userName = Settings.userName;
		routeName = Settings.routeName;
		et = (EditText) findViewById(R.id.textView1);
		rb = (RatingBar) findViewById(R.id.ratingBar1);
		checkInsert();
		ListView list = (ListView) findViewById(R.id.listView1);
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		getCommentValoration();
		ShowCommentsAdapter adapter = new ShowCommentsAdapter(comments, layoutInflater);
		list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
	}
	
	public void addCommentValoration(View view) throws JSONException, InterruptedException, ExecutionException {
		Client clientComment = new Client(Client.SERVLET_COMMENT_INSERT, true);
		Client clientValoration = new Client(Client.SERVLET_VALORATION_INSERT, true);
		String response = "";
		if (!disableComment && !disableValoration) {
			out = Converter.convertCommentToJSONObject(et.getText().toString(), userName, routeName);
			response = clientComment.execute(out).get();
			out = Converter.convertValorationToJSONObject(rb.getRating(), userName, routeName);
			response = response + clientValoration.execute(out).get();
			disableComment = true;
			disableValoration = true;
		} else if (!disableComment) {
			out = Converter.convertCommentToJSONObject(et.getText().toString(), userName, routeName);
			response = clientComment.execute(out).get();
			disableComment = true;
		} else if (!disableValoration) {
			out = Converter.convertValorationToJSONObject((int)rb.getRating(), userName, routeName);
			response = clientValoration.execute(out).get();
			disableValoration = true;
		}
		Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
	}
	
	private void getCommentValoration() {
		Client client = new Client(Client.SERVLET_COMMENT_AND_VALORATION, true);
		try {
			String response = client.execute(routeName).get();
			comments = Converter.convertStringToCommentsValorations(response);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * check if local user has a route comment or valoration in database
	 */
	private void checkInsert() {
		String check = Converter.convertCheckValoratingToJSONObject(userName, routeName);
		// send to the server
		Client checkValoration = new Client(Client.SERVLET_CHECK_VALORATION,true);
		String responseValoration = "";
		Client checkComment = new Client(Client.SERVLET_CHECK_COMMENT,true);
		String responseComment = "";
		String out = check.toString();
		try {
			responseComment = checkComment.execute(out).get();
			responseValoration = checkValoration.execute(out).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		// enable or disable add comments/valorations
		disableValoration = responseValoration.equals(Client.TRUE_CHECK);
		disableComment = responseComment.equals(Client.TRUE_CHECK);
	}

}
