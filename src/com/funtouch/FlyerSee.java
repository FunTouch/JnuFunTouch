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

public class FlyerSee extends Activity {

	private Button btnUse = null;
	private Button btnDelete = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flyer_see);
		showOverflowMenu();
		init();
		btnUse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlyerSee.this, FlyerUse.class);
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
		btnUse = (Button) findViewById(R.id.btn_del_vote);
		btnDelete = (Button) findViewById(R.id.btn_delete);
		
	}

}
