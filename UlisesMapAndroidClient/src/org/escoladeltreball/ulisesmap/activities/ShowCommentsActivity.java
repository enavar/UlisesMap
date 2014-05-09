package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.BaseActivity;
import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowCommentsAdapter;
import org.escoladeltreball.ulisesmap.model.CommentValoration;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcomments);
		ListView list = (ListView) findViewById(R.id.listView1);
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ShowCommentsAdapter adapter = new ShowCommentsAdapter(comments, layoutInflater);
		list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
	}
	
	public void addCommentValoration(View view) throws JSONException {
		// capture user from sharedprefs
		String userName = "";
		String routeName = "";
		if (view.equals(R.id.addComment)) {
			TextView tv = (TextView) findViewById(R.id.textView1);
			String commentText = tv.getText().toString();
			insertComment.put("def", commentText);
		} else {
			RatingBar rb = (RatingBar) findViewById(R.id.ratingBar1);
			int valoration = rb.getNumStars();
			insertComment.put("def", valoration);
		}
		insertComment.put("fk_user", userName);
		insertComment.put("fk_route", routeName);
		
	}

}
