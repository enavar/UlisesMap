package org.escoladeltreball.ulisesmap.activities;


import org.escoladeltreball.ulisesmap.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener {
	
	private Button btnPoints;
	private Button btnRoutes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		btnPoints = (Button) findViewById(R.id.buttonPoint);
		btnRoutes = (Button) findViewById(R.id.buttonRoute);
		btnPoints.setOnClickListener(this);
		btnRoutes.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = (v.equals(btnPoints)) ? 
				new Intent(this, ShowPointsActivity.class) : 
				new Intent(this, ShowRoutesActivity.class);
		startActivity(intent);
	}

}
