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

public class ShowCommentsAdapter extends BaseAdapter {
	
	private ArrayList<CommentValoration> comments;
	private LayoutInflater layoutInflater;
	
	public static class ViewHolder {
		protected TextView description,userName;
		protected RatingBar valoration;
	}

	public ShowCommentsAdapter(ArrayList<CommentValoration> comments,LayoutInflater layoutInflater) {
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
			convertView = layoutInflater.inflate(R.layout.showcomments_list_item, null);
			holder = new ViewHolder();
			holder.valoration = (RatingBar) convertView.findViewById(R.id.valoration);
			holder.description = (TextView) convertView.findViewById(R.id.description);
			holder.userName = (TextView) convertView.findViewById(R.id.userName);
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
