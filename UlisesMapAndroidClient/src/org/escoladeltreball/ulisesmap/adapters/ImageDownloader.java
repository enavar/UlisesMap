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

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;

import org.escoladeltreball.ulisesmap.R;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * ImageDownloader Asynchronously download image from a url using a
 * WeakReference to ensure that the AsyncTask does not prevent the ImageView and
 * anything it references from being garbage collected. Use a ProgressBar to
 * warn a user that image is downloading right now. For guarantee that ImageView
 * is still around when the task finished reference to task itself is saved in
 * AsyncDrawable and after checked in onPostExecute()
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

	/** a references for prevent garbage collection of image */
	private final WeakReference<ImageView> bmImageReferences;
	/** an url of image to download */
	private String url;
	/** a placeholder for image used to find a place to put a downloading image */
	private Bitmap mPlaceHolderBitmap;
	/** android resources */
	private Resources res;
	/** animation to show while image is loading */
	private ProgressBar placeHolder;
	/** a cache used to temporally store downloaded images */
	private LruCache<String, Bitmap> mMemoryCache;

	public ImageDownloader() {
		mPlaceHolderBitmap = null;
		bmImageReferences = null;
	}

	/**
	 * Principal constructor of ImageDownloader
	 * 
	 * @param res
	 *            an android resources
	 * @param imageView
	 *            a view on image will be shown
	 * @param progress
	 *            a ProgressBar to show while image is downloading
	 */
	public ImageDownloader(Resources res, ImageView imageView,
			ProgressBar progress) {
		this.res = res;
		// Use a WeakReference to ensure the ImageView can be garbage collected
		this.bmImageReferences = new WeakReference<ImageView>(imageView);
		mPlaceHolderBitmap = BitmapFactory.decodeResource(res, R.drawable.icon);
		placeHolder = progress;

		// Get max available VM memory, exceeding this amount will throw an
		// OutOfMemory exception. Stored in kilobytes as LruCache takes an
		// int in its constructor.
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 2;

		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

			@SuppressLint("NewApi")
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= 12)
					return bitmap.getByteCount() / 1024;
				else
					return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
			}
		};

	}

	/* Overrided methods */

	@Override
	/**
	 * Decode image in background.
	 */
	protected Bitmap doInBackground(String... urls) {
		String url = urls[0];
		this.url = url;
		Bitmap mIcon = null;
		System.out.println(url);
		try {
			InputStream in = new java.net.URL(url).openStream();
			mIcon = BitmapFactory.decodeStream(in);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mIcon;
	}

	@Override
	/**
	 * Once complete, see if ImageView is still around and set bitmap.
	 */
	protected void onPostExecute(Bitmap result) {
		if (isCancelled()) {
			result = null;
		}
		if (bmImageReferences != null && result != null) {
			final ImageView imageView = bmImageReferences.get();
			final ImageDownloader task = getAssociatedTask(imageView);
			if (this == task && imageView != null) {
				placeHolder.setVisibility(View.GONE);
				imageView.setVisibility(View.VISIBLE);
				addBitmapToMemoryCache(task.url, result);
				imageView.setImageBitmap(result);
			}
		}
	}

	/* Class methods */

	/**
	 * Temporally save an image to a cache
	 * 
	 * @param key
	 *            a url used as key to temporally store an image
	 * @param bitmap
	 *            a image to store
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * Find and return an image stored with given key if exist
	 * 
	 * @param key
	 *            a key used for get an image from cache
	 * @return an image stored in cache
	 */
	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

	/**
	 * Check if already exist a task which download an image from url
	 * 
	 * @param url
	 *            used for download image
	 * @param imageView
	 *            of current item of listview for show image
	 * @return true if the same work is in progress and cancel unnecessary
	 */
	public static boolean cancelPotentialWork(String url, ImageView imageView) {
		final ImageDownloader task = getAssociatedTask(imageView);

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

	// TODO Auto-generated catch block

	/**
	 * Find a asyncTask which download a given image
	 * 
	 * @param imageView
	 *            assigned to asyncTask
	 * @return a asyncTask
	 */
	private static ImageDownloader getAssociatedTask(ImageView imageView) {
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
	 * @param url
	 *            used for download image
	 * @param imageView
	 *            of current item of listview for show image
	 */
	public void loadBitmap(String url, ImageView imageView) {
		final Bitmap bitmap = getBitmapFromMemCache(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			if (cancelPotentialWork(url, imageView)) {
				final ImageDownloader task = new ImageDownloader(res,
						imageView, placeHolder);
				final AsyncDrawable asyncDrawable = new AsyncDrawable(res,
						mPlaceHolderBitmap, task);
				placeHolder.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.INVISIBLE);
				imageView.setImageDrawable(asyncDrawable);
				task.execute(url);
			}
		}
	}

	/* Static class */

	/**
	 * inner class that store a week reference to a task for find a right place
	 * to put an image when task is finished
	 */

	static class AsyncDrawable extends BitmapDrawable {
		/** a references for prevent garbage collection of task */
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
