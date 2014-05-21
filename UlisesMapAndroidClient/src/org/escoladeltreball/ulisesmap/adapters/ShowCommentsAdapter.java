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
package org.escoladeltreball.ulisesmap.adapters;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.CommentValoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * ShowCommentsAdapter A customized adapter for listView that show user's
 * valoration and comments Use an inner class for optimizes work of listView an
 * override it each time new item should be added to listView
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class ShowCommentsAdapter extends BaseAdapter {

	/** set of user's comments */
	private ArrayList<CommentValoration> comments;
	/** layout to inflate */
	private LayoutInflater layoutInflater;

	/**
	 * A ViewHolder object stores each of the component views inside the tag
	 * field of the Layout, so you can immediately access them without the need
	 * to look them up repeatedly.
	 * 
	 */
	public static class ViewHolder {
		/** text fields */
		protected TextView description, userName;
		/** rating bar field */
		protected RatingBar valoration;
	}

	public ShowCommentsAdapter(ArrayList<CommentValoration> comments,
			LayoutInflater layoutInflater) {
		super();
		this.comments = comments;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommentValoration comval = (CommentValoration) getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.showcomments_list_item, null);
			holder = new ViewHolder();
			holder.valoration = (RatingBar) convertView
					.findViewById(R.id.valoration);
			holder.description = (TextView) convertView
					.findViewById(R.id.description);
			holder.userName = (TextView) convertView
					.findViewById(R.id.userName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.userName.setText(comval.getUser());
		holder.valoration.setRating(comval.getVal());
		holder.description.setText(comval.getCom());

		return convertView;
	}

}
