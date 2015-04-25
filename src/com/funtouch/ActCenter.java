package com.funtouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class ActCenter extends MenuHavingActivity{
	private Button btnLaunchAct;
	private Button btnGoAct;
	private Button btnGoVote;
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_center);
		
		btnLaunchAct = (Button) findViewById(R.id.btn_launchact);
		btnGoAct = (Button) findViewById(R.id.btn_goact);
		btnGoVote = (Button) findViewById(R.id.btn_govote);
		
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
