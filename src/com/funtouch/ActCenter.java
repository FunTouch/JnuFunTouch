package com.funtouch;

import com.funtouch.util.SystemBarTintManager;
import com.funtouch.util.SystemBarTintManager.SystemBarConfig;

import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class ActCenter extends MenuHavingActivity{
	private ImageButton btnLaunchAct;
	private ImageButton btnGoAct;
	private ImageButton btnGoVote;
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_center);
		
		btnLaunchAct = (ImageButton) findViewById(R.id.btn_launchact);
		btnGoAct = (ImageButton) findViewById(R.id.btn_goact);
		btnGoVote = (ImageButton) findViewById(R.id.btn_govote);
		
		btnLaunchAct.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(ActCenter.this, AboutActivity.class);
        		startActivity(intent);
        	}
        });
		
		
		btnGoAct.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(ActCenter.this, MySignUp.class);
        		startActivity(intent);
        	}
        });
		
		btnGoVote.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(ActCenter.this, MyVote.class);
        		startActivity(intent);
        	}
        });
	}

}
