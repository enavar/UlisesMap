package org.escoladeltreball.ulisesmap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

		}
		
		   // Initiating Menu XML file (menu.xml)
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.menu, menu);
	        return true;
	    }
	     
	    /**
	     * Event Handling for Individual menu item selected
	     * Identify single menu item by it's id
	     * */
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	         
	        switch (item.getItemId())
	        {
	 
	        case R.id.menu_search:
	            Toast.makeText(this, "Search is Selected", Toast.LENGTH_SHORT).show();
	            return true;
	 
	        case R.id.menu_share:
	            Toast.makeText(this, "Share is Selected", Toast.LENGTH_SHORT).show();
	            return true;
	 
	        case R.id.myGPS:
	        	if (item.isChecked()) item.setChecked(false);
	            else item.setChecked(true);
	            return false; 
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
}