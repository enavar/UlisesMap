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
package org.escoladeltreball.ulisesmap.activities;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Settings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Main activity which show animated text with name of application and animated
 * logo
 * 
 * @author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class UlisesActivity extends Activity {

	/** delay before start another task */
	private final int delay = 2000;
	/** animation for application name */
	private Animation textAnimation;
	/** application name */
	private TextView app_name;

	
	/** 
	 * Check if logo is skipped and if user is already has been log in earlier
	 * otherwise initiate animation 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ulises);
		if (Settings.hideLogo) {
			if (Settings.userName.equals(null)) {
				startActivity(new Intent(this, LoginActivity.class));
				finish();
			} else {
				startActivity(new Intent(this, MenuActivity.class));
				finish();
			}
		} else {
			app_name = (TextView) findViewById(R.id.mainTitle);
			textAnimation = AnimationUtils.loadAnimation(this,
					android.R.anim.slide_in_left);
			textAnimation.setDuration(2000);
		}

	}

	/**
	 * Will launch the activity
	 */
	private Runnable mLaunchTask = new Runnable() {
		public void run() {
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);
			finish();
		}
	};

	/**
	 * will launch a text animation
	 */
	private Runnable textAnimTask = new Runnable() {
		public void run() {
			app_name.startAnimation(textAnimation);
			app_name.setText(R.string.app_name);
			Handler mHandler = new Handler();
			mHandler.postDelayed(mLaunchTask, delay);
		}
	};

	@Override
	/**
	 * When all object are loaded and window has focus start a logo animation
	 */
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			ImageView image = (ImageView) findViewById(R.id.animation);
			image.setAdjustViewBounds(true);
			// image.setBackgroundResource(R.drawable.animation_orca);
			final AnimationDrawable frameAnimation = (AnimationDrawable) getResources()
					.getDrawable(R.drawable.animation_orca);
			image.setImageDrawable(frameAnimation);
			frameAnimation.start();
			Handler textAnimHandler = new Handler();
			textAnimHandler.postDelayed(textAnimTask, delay);
		}
	}
}
