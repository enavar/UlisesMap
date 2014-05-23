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

public class ShowCommentsActivity extends BaseActivity {
	
	private ArrayList<CommentValoration> comments;
	private String userName;
	private String routeName; 
	private boolean disableComment = false;
	private boolean disableValoration = false;
	private EditText et;
	private RatingBar rb;
	private String out;
	private String no_insert_text = null;
	private double no_insert_value = 0.0;
	private boolean isAnonim = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcomments);
		userName = Settings.userName;
		isAnonim = userName.equals("");
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
	
	/**
	 * Add a comment, valoration to the db
	 * @param view the button for press
	 * @throws JSONException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void addCommentValoration(View view) throws JSONException, InterruptedException, ExecutionException {
		// if user is anonim starts the register activity
		if (isAnonim) {
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
		}
		// conection to servlet
		Client clientComment = new Client(Client.SERVLET_COMMENT_INSERT, true);
		Client clientValoration = new Client(Client.SERVLET_VALORATION_INSERT, true);
		String response = getString(R.string.sorry);
		String text = et.getText().toString();
		double value = rb.getRating();
		// conditional posibility for insert in db
		if (!disableComment && !disableValoration && !text.equals(no_insert_text) && value != no_insert_value) {
			out = Converter.convertCommentToJSONObject(text, userName, routeName);
			clientComment.execute(out).get();
			out = Converter.convertValorationToJSONObject(value, userName, routeName);
			clientValoration.execute(out).get();
			response = getString(R.string.ok_comment_valoration);
		} else if (!disableComment && !text.equals(no_insert_text)) {
			out = Converter.convertCommentToJSONObject(text, userName, routeName);
			clientComment.execute(out).get();
			response = getString(R.string.ok_comment);
		} else if (!disableValoration && value != no_insert_value) {
			out = Converter.convertValorationToJSONObject(value, userName, routeName);
			clientValoration.execute(out).get();
			response = getString(R.string.ok_valoration);
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
		System.out.println("response " + responseValoration);
		disableValoration = responseValoration.equals(Client.TRUE_CHECK);
		disableComment = responseComment.equals(Client.TRUE_CHECK);
	}

}
