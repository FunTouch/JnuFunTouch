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

public class GameList extends MenuHavingActivity {

	private Button btnUndercover = null;
	private Button btnWerewolf = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		showOverflowMenu();
		init();
		
		btnUndercover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GameList.this, GameUndercoverSetting.class);
				startActivity(intent);
			}
		});
		
		btnWerewolf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GameList.this, GameWerewolfSetting.class);
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
		btnUndercover = (Button) findViewById(R.id.btn_who_is_undercover);
		btnWerewolf = (Button) findViewById(R.id.btn_werewolf);
		
	}

}
