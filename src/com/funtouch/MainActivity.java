package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	public Cookie application ; 

	private static boolean isSocialFirstUse = true;
	
	private Button btnFunTouch = null;
	private Button btnSocial = null;
	private Button btnAct = null;
	private Button btnSetting = null;
	private Button btnGame = null;
	private Button btnLogin = null;
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
		
		btnSocial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				
				if (isSocialFirstUse)
					intent.setClass(MainActivity.this, SocialFirstUse.class);
				else
					intent.setClass(MainActivity.this, SocialMyCard.class);
				
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
	}

	private void init() {
		btnFunTouch = (Button) findViewById(R.id.btn_funtouch);
		btnAct = (Button) findViewById(R.id.btn_about_activity);
		btnSocial = (Button) findViewById(R.id.btn_social);
		btnSetting = (Button) findViewById(R.id.btn_setting);
		btnGame = (Button) findViewById(R.id.btn_game_help);
		btnLogin = (Button) findViewById(R.id.btn_login);

	}
	
	public static void setSocialStatus(boolean status)
	{
		isSocialFirstUse = status;
	}
	
	//双击返回键退出
	public boolean onKeyDown(int keyCode, KeyEvent event)   
	{  
	    // TODO Auto-generated method stub  	
	  
	    if((keyCode == KeyEvent.KEYCODE_BACK)&&(event.getAction() == KeyEvent.ACTION_DOWN))  
	    {  
	    	  
	        if(System.currentTimeMillis() - temptime >2000) // 2s内再次选择back键有效   
	        {  
	            System.out.println(Toast.LENGTH_LONG);  
	            Toast.makeText(this, "请再按一次返回退出", Toast.LENGTH_LONG).show();  
	            temptime = System.currentTimeMillis();  
	        }  
	        else {  
	               finish();   
	               System.exit(0); //凡是非零都表示异常退出!0表示正常退出!   
	        }  
	             
	        return true;   
	  
	    }  
	    return super.onKeyDown(keyCode, event);  
	}  

}
