package com.funtouch;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;

public class FlyerNew extends Activity {

	private ImageButton btnImportImg = null;
	private ImageButton btnAddDetail = null;
	private ImageButton btnSelectStyle = null;
	private ImageButton btnOk = null;
	private ImageButton btnDelete = null;
	private ImageButton btnPreview = null;
	//private Button btnReturn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_flyer);
		showOverflowMenu();
		init();

		btnSelectStyle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlyerNew.this, FlyerNewSelectStyle.class);
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_flyernew, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	private void init() {
		btnImportImg = (ImageButton) findViewById(R.id.btn_import_image);
		btnAddDetail = (ImageButton) findViewById(R.id.btn_add_detail);
		btnSelectStyle = (ImageButton) findViewById(R.id.btn_select_style);
		btnOk = (ImageButton) findViewById(R.id.btn_ok);
		btnDelete = (ImageButton) findViewById(R.id.btn_delete);
		btnPreview = (ImageButton) findViewById(R.id.btn_preview);
		
	}

}
