/**
 * Copyright (c) 2014, Oleksander Dovbysh & Elisabet Navarro & Sheila Perez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.escoladeltreball.ulisesmap.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.adapters.ShowCommentsAdapter;
import org.escoladeltreball.ulisesmap.connections.Client;
import org.escoladeltreball.ulisesmap.converter.Converter;
import org.escoladeltreball.ulisesmap.model.CommentValoration;
import org.escoladeltreball.ulisesmap.model.Settings;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
/**
 * ShowCommentActivity
 * Show all comments and ratings.
 * Given the option to insert new comments and ratings.
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class ShowCommentsActivity extends BaseActivity {
	
	/** List of comments */
	private ArrayList<CommentValoration> comments;
	/** Name of user */
	private String userName;
	/** Name of route */
	private String routeName; 
	/** State option of comment */
	private boolean disableComment = false;
	/** State option of asses */
	private boolean disableValoration = false;
	/** Field definition of new comment */
	private EditText editTextDefinition;
	/** New value ranking of route */ 
	private RatingBar rankingValue;
	/** Default value when no comment. */
	private static final String NO_INSERT_COMMENT = null;
	/** Default value when no rating. */
	private static final double NO_INSERT_VALUE = 0.0;
	/** State option anonymous */
	private boolean isAnonymous = false;
	
	/**
	 * Create ShowCommentActivity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcomments);
		userName = Settings.userName;
		isAnonymous = userName.equals("");
		routeName = Settings.routeName;
		editTextDefinition = (EditText) findViewById(R.id.textView1);
		rankingValue = (RatingBar) findViewById(R.id.ratingBar1);
		checkInsert();
		ListView list = (ListView) findViewById(R.id.listView1);
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		getCommentValoration();
		ShowCommentsAdapter adapter = new ShowCommentsAdapter(comments, layoutInflater);
		list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
	}
	
	/**
	 * Add a comment, valoration to the db
	 * 
	 * @param view the button for press
	 * @throws JSONException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void addCommentValoration(View view) throws JSONException, InterruptedException, ExecutionException {
		// if user is anonim starts the register activity
		String out = new String();
		if (isAnonymous) {
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
		}
		// conection to servlet
		Client clientComment = new Client(Client.SERVLET_COMMENT_INSERT, true);
		Client clientValoration = new Client(Client.SERVLET_VALORATION_INSERT, true);
		String response = getString(R.string.sorry);
		String text = editTextDefinition.getText().toString();
		double value = rankingValue.getRating();
		// conditional posibility for insert in db
		if (!disableComment && !disableValoration && !text.equals(NO_INSERT_COMMENT) && value != NO_INSERT_VALUE) {
			out = Converter.convertCommentToJSONObject(text, userName, routeName);
			clientComment.execute(out).get();
			out = Converter.convertValorationToJSONObject(value, userName, routeName);
			clientValoration.execute(out).get();
			response = getString(R.string.ok_comment_valoration);
		} else if (!disableComment && !text.equals(NO_INSERT_COMMENT)) {
			out = Converter.convertCommentToJSONObject(text, userName, routeName);
			clientComment.execute(out).get();
			response = getString(R.string.ok_comment);
		} else if (!disableValoration && value != NO_INSERT_VALUE) {
			out = Converter.convertValorationToJSONObject(value, userName, routeName);
			clientValoration.execute(out).get();
			response = getString(R.string.ok_valoration);
		} 
		if (isAnonymous) {
			response = getString(R.string.anonymous_users_false);
		}
		Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Gets the comments and valorations from the db and prepare to display in activity
	 */
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
	 * Check if local user has a route comment or valoration in database
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
		disableValoration = responseValoration.equals(Client.TRUE_CHECK);
		disableComment = responseComment.equals(Client.TRUE_CHECK);
	}

}
