package org.escoladeltreball.ulisesmap.adapters;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.Point;
import org.escoladeltreball.ulisesmap.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPointsAdapter extends BaseAdapter {

	ArrayList<Point> points;
	private LayoutInflater layoutInflater;

	public static class ViewHolder {
		protected ImageView image;
		protected TextView name, coord, description;

	}
	
	

	public ShowPointsAdapter(ArrayList<Point> points,
			LayoutInflater layoutInflater) {
		super();
		this.points = points;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount() {
		return points.size();
	}

	@Override
	public Object getItem(int position) {
		return points.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Point point = points.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.showpoints_list_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.coord = (TextView)convertView.findViewById(R.id.coord);
			holder.description = (TextView)convertView.findViewById(R.id.description);
			convertView.setTag(holder);
		} else 
			holder = (ViewHolder)convertView.getTag();
		new ImageDownloader(holder.image).execute(point.getImage());
		holder.name.setText(point.getName());
		holder.coord.setText(point.getGp().toString());
		holder.description.setText(point.getDescription());
		return convertView;
	}
}
