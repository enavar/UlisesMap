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
import java.util.HashSet;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Point;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * ShowPointsAdapter
 * A customized adapter for listView that show points of interest .
 * Use an inner class for optimizes work of listView an
 * override it each time new item should be added to listView
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class ShowPointsAdapter extends BaseAdapter {

	/** a set of points to show */
	private ArrayList<Point> points;
	/** a set of selected points */
	private HashSet<Point> pointsCheck;
	/** layout to inflate */
	private LayoutInflater layoutInflater;
	/** android resources */
	private Resources res;

	/**
	 * A ViewHolder object stores each of the component views inside the tag
	 * field of the Layout, so you can immediately access them without the need
	 * to look them up repeatedly.
	 * 
	 */
	public static class ViewHolder {
		/** a image field*/
		protected ImageView image;
		/** text fields */
		protected TextView name, street, url;
		/** checkbox field */
		protected CheckBox chBox;
	}

	/**
	 * Constructor
	 * 
	 * @param points a set of points
	 * @param layoutInflater LayoutInflater to inflate rows of ListView
	 * @param res android Resources
	 */
	public ShowPointsAdapter(Resources res, ArrayList<Point> points,
	LayoutInflater layoutInflater) {
		super();
		this.res = res;
		this.points = points;
		this.layoutInflater = layoutInflater;
		this.pointsCheck = new HashSet<Point>();
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
		final Point point = (Point) getItem(position);
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
			holder.street = (TextView) convertView.findViewById(R.id.street);
			holder.url = (TextView) convertView.findViewById(R.id.url);
			holder.chBox = (CheckBox) convertView
					.findViewById(R.id.selectPoint);
			convertView.setTag(holder);
		} else
			// Get the ViewHolder back to get fast access to the TextView                
		    // and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		addListenerCheckBox(holder, point);
		addDialog(convertView, point);
		//download image
		ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.placeHolder);
		ImageDownloader task = new ImageDownloader(res, holder.image, progress);
		task.loadBitmap(point.getImage(), holder.image);
		//assign values to Point object
		holder.name.setText(point.getName());
		holder.street.setText(point.getStreet());
		holder.url.setMovementMethod(LinkMovementMethod.getInstance());
		holder.url.setText(Html.fromHtml(point.getUrl()));
		holder.chBox.setChecked(point.isSelected());
		holder.chBox.setTag(point);
		return convertView;
	}
	
	/**
	 * 
	 * @param holder 
	 * @param point
	 */
	private void addListenerCheckBox(ViewHolder holder, final Point point) {
		holder.chBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				point.setSelected(cb.isChecked());
				if (pointsCheck.contains(point))
					pointsCheck.remove(point);
				else
					pointsCheck.add(point);
			}
		});
		
	}

	private void addDialog(View view, final Point point) {
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setTitle(point.getName());
				builder.setMessage(point.getDescription());
				builder.setCancelable(true);
				builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       dialog.cancel();
	                   }
	               });
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
	}

	public HashSet<Point> getPointsCheck() {
		return pointsCheck;
	}

}
