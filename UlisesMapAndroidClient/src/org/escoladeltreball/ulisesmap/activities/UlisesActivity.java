package org.escoladeltreball.ulisesmap.activities;

import org.escoladeltreball.ulisesmap.R;
import org.escoladeltreball.ulisesmap.model.Settings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class UlisesActivity extends Activity {

	private final int delay = 2000;

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
			Handler mHandler = new Handler();
			mHandler.postDelayed(mLaunchTask, delay);		
			TextView app_name = (TextView) findViewById(R.id.mainTitle);
			app_name.setText(R.string.app_name);
			app_name.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
		}

	}

	// will launch the activity
	private Runnable mLaunchTask = new Runnable() {
		public void run() {
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);
			finish();
		}
	};
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);
	    if (hasFocus) {
	    	ImageView image = (ImageView) findViewById(R.id.animation);
			image.setAdjustViewBounds(true);
			image.setBackgroundResource(R.drawable.animation_orca);
			final AnimationDrawable frameAnimation = (AnimationDrawable)getResources().getDrawable(R.drawable.animation_orca);
			image.setImageDrawable(frameAnimation);
			frameAnimation.start();
	    }
	}
}
