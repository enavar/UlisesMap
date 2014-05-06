package org.escoladeltreball.ulisesmap;

import org.escoladeltreball.ulisesmap.model.Settings;

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
	        menu.findItem(Settings.routeType).setChecked(true);
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
	 
	        case R.id.car:
	        	changeMenuItemStatus(item);	            
	        case R.id.bicycle:
	        	changeMenuItemStatus(item);	            
	        case R.id.walk:
	        	changeMenuItemStatus(item);	 
	        case R.id.myGPS:
	        	if (item.isChecked()) item.setChecked(false);
	            else item.setChecked(true);
	            return true; 
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }

	    public void changeMenuItemStatus(MenuItem item) {
	    	if (item.isChecked()) {
	    		item.setChecked(false);
	    		Settings.routeType = item.getItemId();
	    	} else {
	    		item.setChecked(true);
	    	}
	    }
}