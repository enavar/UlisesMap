package org.escoladeltreball.ulisesmap.adapters;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Route;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

public class ShowRoutesAdapter extends BaseAdapter {
	
	private ArrayList<Route> routes;
	private LayoutInflater layoutInflater;
	
	public static class ViewHolder {
		protected TextView name, totalPoints;
		protected CheckBox chBox;
		protected Spinner points;
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
		return routes.indexOf(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Route route = (Route)getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.showroutes_list_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.viewNameRoute);
			holder.totalPoints = (TextView) convertView.findViewById(R.id.viewNumPoints);
			holder.points = (Spinner) convertView.findViewById(R.id.listPoints);
			holder.chBox = (CheckBox) convertView.findViewById(R.id.selectRoute);
			holder.valoration = (RatingBar) convertView.findViewById(R.id.ratingValue);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(route.getName());
		holder.totalPoints.setText(route.totalPoints());
		String[] namesPoints = route.getNamesPoints();
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				convertView.getContext(), android.R.layout.simple_spinner_item, namesPoints);
		holder.points.setAdapter(spinnerArrayAdapter);
		holder.valoration.setRating(route.valorationAverage());
		return convertView;
	}

}
