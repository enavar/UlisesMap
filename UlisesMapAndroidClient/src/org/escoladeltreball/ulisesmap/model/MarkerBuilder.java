package org.escoladeltreball.ulisesmap.model;

import java.util.concurrent.ExecutionException;

import org.escoladeltreball.ulisesmap.adapters.ImageDownloader;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MarkerBuilder extends Marker {

	GeoPoint point;
	String imgUrl;
	String desc;
	String name;
	Resources res;
	ImageDownloader downloader;
	private final int IMAGE_SIZE = 50;

	public MarkerBuilder(MapView mapView, Resources res, GeoPoint point, String imgUrl, String desc, String name) {
		super(mapView);
		this.res = res;
		this.point = point;
		this.imgUrl = imgUrl;
		this.name = name;
		this.desc = desc;
		this.downloader = new ImageDownloader();
	}	
	
	public Marker build() {
		this.setPosition(point);
		try {
			Bitmap bitmap = downloader.execute(imgUrl).get();
			Bitmap cutted = cutImage(bitmap);
			Drawable smallImg = new BitmapDrawable(res,
					Bitmap.createScaledBitmap(cutted, IMAGE_SIZE,
							IMAGE_SIZE, false));
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
