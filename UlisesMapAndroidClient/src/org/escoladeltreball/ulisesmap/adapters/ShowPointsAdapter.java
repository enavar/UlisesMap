package org.escoladeltreball.ulisesmap.adapters;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Point;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPointsAdapter extends BaseAdapter {

	ArrayList<Point> points;
	private LayoutInflater layoutInflater;

	public static class ViewHolder {
		protected ImageView image;
		protected TextView name, coord, description;
		protected CheckBox chBox;
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
		Point point = (Point) getItem(position);
		// A ViewHolder keeps references to children views to avoid unneccessary calls            
		// to findViewById() on each row. 
		ViewHolder holder = null;
		// When convertView is not null, we can reuse it directly, there is no need            
		// to reinflate it. We only inflate a new View when the convertView supplied            
		// by ListView is null.  
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.showpoints_list_item,
					null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.coord = (TextView) convertView.findViewById(R.id.coord);
			holder.description = (TextView) convertView
					.findViewById(R.id.description);			
			holder.chBox = (CheckBox) convertView
					.findViewById(R.id.selectPoint);
			convertView.setTag(holder);
			holder.chBox.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					Point point = (Point) cb.getTag();
					point.setSelected(cb.isChecked());
				}
			});
		} else
			// Get the ViewHolder back to get fast access to the TextView                
		    // and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		//download image
		//new ImageDownloader(holder.image).execute(point.getImage());
		UrlImageViewHelper.setUrlDrawable(holder.image, point.getImage());
		//get the current Point object
		Point p = points.get(position);
		//assign values to Point object
		holder.name.setText(point.getName());
		holder.coord.setText(point.getGp().toString());
		holder.description.setText(point.getDescription());
		holder.chBox.setChecked(p.isSelected());
		holder.chBox.setTag(p);
		return convertView;
	}

}
