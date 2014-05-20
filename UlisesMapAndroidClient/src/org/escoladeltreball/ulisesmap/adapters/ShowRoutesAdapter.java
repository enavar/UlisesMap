package org.escoladeltreball.ulisesmap.adapters;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Route;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShowRoutesAdapter extends BaseAdapter {
	
	private ArrayList<Route> routes;
	private LayoutInflater layoutInflater;
	Resources res;
	
	public static class ViewHolder {
		protected TextView name, description;
		protected RatingBar valoration;
		protected ImageView image;
	}

	public ShowRoutesAdapter(ArrayList<Route> routes,
			LayoutInflater layoutInflater, Resources res) {
		super();
		this.routes = routes;
		this.layoutInflater = layoutInflater;
		this.res = res;
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
			holder.image = (ImageView) convertView.findViewById(R.id.imageRoute);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.placeHolderRoute);
		ImageDownloader task = new ImageDownloader(res, holder.image, progress);
		task.loadBitmap(route.getImage(), holder.image);
		holder.name.setText(route.getName());
		holder.valoration.setRating((float) route.getValorationAverage());
		holder.description.setText(route.getDescription());
		return convertView;
	}

}
