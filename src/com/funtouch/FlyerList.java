package com.funtouch;
import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;


public class FlyerList extends Activity {

	private Button btnNewFlyer = null;
	private Button btnSeeFlyer = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flyer_list);
		showOverflowMenu();
		init();
		
		btnNewFlyer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlyerList.this, FlyerNew.class);
				startActivity(intent);
			}
		});
		
		btnSeeFlyer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlyerList.this, FlyerSee.class);
				startActivity(intent);
			}
		});
		
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
	private void init() {
		btnNewFlyer = (Button) findViewById(R.id.btn_new_flyer);
		btnSeeFlyer =  (Button) findViewById(R.id.btn_see_flyer);
		
	}

}
