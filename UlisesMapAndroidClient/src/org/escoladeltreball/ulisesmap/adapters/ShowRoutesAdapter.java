package org.escoladeltreball.ulisesmap.adapters;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Route;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShowRoutesAdapter extends BaseAdapter {
	
	private ArrayList<Route> routes;
	private LayoutInflater layoutInflater;
	
	public static class ViewHolder {
		protected TextView name, description;
		protected RatingBar valoration;
	}

	public ShowRoutesAdapter(ArrayList<Route> routes,
			LayoutInflater layoutInflater) {
		super();
		this.routes = routes;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount() {
		return routes.size();
	}

	@Override
	public Object getItem(int position) {
		return routes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println(position);
		System.out.println(getItem(position).getClass());
		Route route = (Route)getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.showroutes_list_item, null);
			holder = new ViewHolder();
			holder.valoration = (RatingBar) convertView.findViewById(R.id.ratingValue);
			holder.description =(TextView) convertView.findViewById(R.id.viewDescriptionRoute);
			holder.name = (TextView) convertView.findViewById(R.id.viewNameRoute);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(route.getName());
		holder.valoration.setRating(route.getValorationAverage());
		holder.description.setText(route.getDescription());
		return convertView;
	}

}
