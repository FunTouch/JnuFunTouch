package com.funtouch;

import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

public class FlyerUse extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flyer_use);
		showOverflowMenu();
	}
	
	public void showOverflowMenu(){
		try {  
	        ViewConfiguration config =ViewConfiguration.get(this);  
	        Field menuKeyField = ViewConfiguration.class.
	        getDeclaredField("sHasPermanentMenuKey");  
	        if(menuKeyField != null) {  
	         menuKeyField.setAccessible(true);  
	         menuKeyField.setBoolean(config, false);  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}



@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_overflow, menu);
		return super.onCreateOptionsMenu(menu);
	}




@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.menu_aboutus:
			
			break;
		case R.id.menu_help:
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}


}
