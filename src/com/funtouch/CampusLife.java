package com.funtouch;

import com.funtouch.util.SystemBarTintManager;
import com.funtouch.util.SystemBarTintManager.SystemBarConfig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class CampusLife extends MenuHavingActivity  {
	private Button btnGame,btnContact,btnBill = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.royalblue);
            SystemBarConfig config = tintManager.getConfig();
            
		}
		setContentView(R.layout.campus_life);
		
		btnGame = (Button) findViewById(R.id.btn_game);
		btnContact = (Button) findViewById(R.id.btn_contact);
		btnBill = (Button) findViewById(R.id.btn_bill);
		
		btnGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CampusLife.this, GameList.class);
				startActivity(intent);
			}
		});
		
		btnContact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CampusLife.this, CardTransport.class);
				startActivity(intent);
			}
		});
		
		btnBill.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CampusLife.this, CheckBill.class);
				startActivity(intent);
			}
		});
	}
			
		
	

}
