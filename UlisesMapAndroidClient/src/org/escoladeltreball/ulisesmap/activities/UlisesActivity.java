package org.escoladeltreball.ulisesmap.activities;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class UlisesActivity extends Activity {
	
	private final int delay = 2000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Handler mHandler = new Handler();

        mHandler.postDelayed(mLaunchTask,delay);
		
	}

    //will launch the activity
    private Runnable mLaunchTask = new Runnable() {
        public void run() {
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }
     };

}
