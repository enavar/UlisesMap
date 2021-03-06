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
package org.escoladeltreball.ulisesmap.model;

import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.adapters.ImageDownloader;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * MarkerBuilder
 * Prepare a marker to be shown at the map. Download and set all
 * it properties.
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class MarkerBuilder extends Marker {

	/** coordinates of marker */
	GeoPoint point;
	/** url to load a marker image */
	String imgUrl;
	/** description of marker */
	String desc;
	/** marker name */
	String name;
	/** android resources for create a bitmap */
	Resources res;
	/** asynchronous task for load image */
	ImageDownloader downloader;
	/** final image size */
	private final int IMAGE_SIZE = 50;

	/**
	 * Constructor
	 * 
	 * @param mapView
	 *            a map to show a marker
	 * @param res
	 *            an android resources
	 * @param point
	 *            coordinates of marker
	 * @param imgUrl
	 *            url to load a marker image
	 * @param desc
	 *            description of marker
	 * @param name
	 *            android resources for create a bitma
	 */
	public MarkerBuilder(MapView mapView, Resources res, GeoPoint point,
			String imgUrl, String desc, String name) {
		super(mapView);
		this.res = res;
		this.point = point;
		this.imgUrl = imgUrl;
		this.name = name;
		this.desc = desc;
		this.downloader = new ImageDownloader();
	}

	/**
	 * Set all properties of a marker like name, image, description. Download
	 * and prepare image to assign to a marker
	 * 
	 * @return a marker ready to be shown at map
	 */
	public Marker build() {
		this.setPosition(point);
		try {
			Bitmap bitmap = downloader.execute(imgUrl).get();
			Bitmap cutted = cutImage(bitmap);
			Drawable smallImg = new BitmapDrawable(res,
					Bitmap.createScaledBitmap(cutted, IMAGE_SIZE, IMAGE_SIZE,
							false));
			Drawable image = new BitmapDrawable(res, bitmap);
			this.setIcon(smallImg);
			this.setImage(image);
			this.setSubDescription(desc);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		// nodeMarker.setIcon(nodeIcon);
		this.setTitle(name);
		return this;
	}

	/**
	 * Make a circular image from rectangular one
	 * 
	 * @param input
	 *            a bitmap to be processed
	 * @return a cut image
	 */
	public Bitmap cutImage(Bitmap input) {
		Bitmap output = Bitmap.createBitmap(input.getWidth(),
				input.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, input.getWidth(), input.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(input.getWidth() / 2, input.getHeight() / 2,
				input.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(input, rect, rect, paint);
		return output;
	}

}
