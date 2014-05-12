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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShowCommentsActivity extends BaseActivity {
	
	private ArrayList<CommentValoration> comments;
	private JSONObject insertComment;
	private String userName;
	private String routeName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcomments);
		ListView list = (ListView) findViewById(R.id.listView1);
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ShowCommentsAdapter adapter = new ShowCommentsAdapter(comments, layoutInflater);
		userName = Settings.userName;
		routeName = Settings.routeName;
		getCommentValoration();
		list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
	}
	
	public void addCommentValoration(View view) throws JSONException, InterruptedException, ExecutionException {
		// capture user and route from Settings
		insertComment.put("fk_user", userName);
		insertComment.put("fk_route", routeName);
		// prepare client output
		Client client = null;
		if (view.equals(R.id.addComment)) {
			TextView tv = (TextView) findViewById(R.id.textView1);
			String commentText = tv.getText().toString();
			insertComment.put("def", commentText);
			client = new Client(Client.SERVLET_COMMENT_INSERT, true);
			
		} else {
			RatingBar rb = (RatingBar) findViewById(R.id.ratingBar1);
			int valoration = rb.getNumStars();
			insertComment.put("def", valoration);
			client = new Client(Client.SERVLET_VALORATION_INSERT, true);
		}
		// output to server
		String out = insertComment.toString();
		client.execute(out).get();
		
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

}
