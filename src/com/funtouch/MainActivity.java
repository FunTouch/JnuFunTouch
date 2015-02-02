package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private Button btnFunTouch = null;
	private Button btnCardTransport = null;
	private Button btnAct = null;
	private Button btnSetting = null;
	private Button btnGame = null;
	private Button btnLogin = null;
	private Button btnValidatePass = null;
	public Cookie application ; 
	private long temptime = 0;
	String cookie = application.getInstance().getCookie();
	String name = application.getInstance().getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		
		btnFunTouch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, FunTouchProfile.class);
				startActivity(intent);
			}
		});
		
		btnAct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AboutActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		btnGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GameList.class);
				startActivity(intent);
			}
		});
		
		btnCardTransport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CardTransport.class);
				startActivity(intent);
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Login.class);
				startActivity(intent);
				finish();
			}
		});
		
		if(cookie!=null)
		{
			btnLogin.setText(name);
		}
		
		btnValidatePass.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, validatePassActivity.class);
				startActivity(intent);
			}
			
		});
	}
	
	//双击返回键退出程序
	public boolean onKeyDown(int keyCode, KeyEvent event)   
	{  
	    // TODO Auto-generated method stub  	
	  
	    if((keyCode == KeyEvent.KEYCODE_BACK)&&(event.getAction() == KeyEvent.ACTION_DOWN))  
	    {  
	    	  
	        if(System.currentTimeMillis() - temptime >2000) // 2s内再按一次返回键退出   
	        {    
	            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_LONG).show();  
	            temptime = System.currentTimeMillis();  
	        }  
	        else {  
	               finish();   
	               System.exit(0); //程序正常退出  
	        }  
	             
	        return true;   
	  
	    }  
	    return super.onKeyDown(keyCode, event);  
	}  
	private void init() {
		btnFunTouch = (Button) findViewById(R.id.btn_funtouch);
		btnAct = (Button) findViewById(R.id.btn_about_activity);
		btnCardTransport = (Button) findViewById(R.id.btnCardTransport);
		btnSetting = (Button) findViewById(R.id.btn_setting);
		btnGame = (Button) findViewById(R.id.btn_game_help);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnValidatePass = (Button) findViewById(R.id.btn_validate_pass);
	}
	
	
	
	
}
