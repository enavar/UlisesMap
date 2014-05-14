package org.escoladeltreball.ulisesmap.adapters;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.escoladeltreball.ulisesmap.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
	
	private final WeakReference<ImageView> bmImageReferences;
	private String url;
	Bitmap mPlaceHolderBitmap;
	Resources res;

	public ImageDownloader(Resources res, ImageView imageView) {
		this.res = res;
		// Use a WeakReference to ensure the ImageView can be garbage collected
		this.bmImageReferences = new WeakReference<ImageView>(imageView);
		mPlaceHolderBitmap = BitmapFactory.decodeResource(res, R.drawable.upload);
	}
	
	/* Overrided methods*/

	// Decode image in background.
	@Override
	protected Bitmap doInBackground(String... urls) {
		String url = urls[0];
		Bitmap mIcon = null;
		try {
			InputStream in = new java.net.URL(url).openStream();
			mIcon = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		return mIcon;
	}

	// Once complete, see if ImageView is still around and set bitmap.
	@Override
	protected void onPostExecute(Bitmap result) {
		if (isCancelled()) {
			result = null;
		}
		if (bmImageReferences != null && result != null) {
			final ImageView imageView = bmImageReferences.get();
			final ImageDownloader task = getAssociatedtask(imageView);
			if (this == task && imageView != null) {
				imageView.setImageBitmap(result);
			}
		}
	}
	
	/* Class methods */

	/**
	 * Check if already exist a task which download an image from url
	 * 
	 * @param url used for download image
	 * @param imageView of current item of listview for show image
	 * @return true if the same work is in progress and cancel unnecessary
	 */
	public static boolean cancelPotentialWork(String url, ImageView imageView) {
		final ImageDownloader task = getAssociatedtask(imageView);

		if (task != null) {
			final String bitmapData = task.url;
			// If bitmapData is not yet set or it differs from the new data
			if (bitmapData == null || !bitmapData.equals(url)) {
				// Cancel previous task
				task.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}
		// No task associated with the ImageView, or an existing task was
		// cancelled
		return true;
	}

	/**
	 * Find a asyncTask which download a given image
	 * 
	 * @param imageView assigned to asyncTask
	 * @return a asyncTask
	 */
	private static ImageDownloader getAssociatedtask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}

	/**
	 * Initiate a asynchronous download of image from url
	 * 
	 * @param url used for download image
	 * @param imageView of current item of listview for show image
	 */
	public void loadBitmap(String url, ImageView imageView) {
		if (cancelPotentialWork(url, imageView)) {
			final ImageDownloader task = new ImageDownloader(res, imageView);
			final AsyncDrawable asyncDrawable = new AsyncDrawable(res,
					mPlaceHolderBitmap, task);
			imageView.setImageDrawable(asyncDrawable);
			task.execute(url);
		}
	}

	/* Static class */

	static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<ImageDownloader> taskReferences;

		public AsyncDrawable(Resources res, Bitmap bitmap,
				ImageDownloader imageDownloader) {
			super(res, bitmap);
			taskReferences = new WeakReference<ImageDownloader>(imageDownloader);
		}

		public ImageDownloader getBitmapWorkerTask() {
			return taskReferences.get();
		}
	}
}