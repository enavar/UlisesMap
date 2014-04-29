package org.escoladeltreball.ulisesmap.activities;

import org.escoladeltreball.ulisesmap.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegisterActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Button btn = (Button) findViewById(R.id.registerAdd);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
	}

}
