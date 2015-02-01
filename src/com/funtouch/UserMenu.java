package com.funtouch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserMenu extends Activity{

	private Button btnVote = null;
	private Button btnSignUp = null;
	private Button btnFlyer = null;
	
	private Button btnLogoff = null;
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	private String name;
	List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_menu);
		 
		init();
		
		objectList = (List<ActDetailsInfo>) getIntent().getSerializableExtra("ListObject");
		
		btnLogoff = (Button) findViewById(R.id.btn_logoff);
		
		btnLogoff.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){

        		application.getInstance().setCookie(null);    		
        		Intent intent=new Intent();
        		intent.setClass(UserMenu.this, MainActivity.class);	
        		startActivity(intent);
        		finish();
        	}
        });	
		btnVote.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent=new Intent();
	        		intent.setClass(UserMenu.this, VoteEdit.class);
	        		startActivity(intent);
		
	        	}
	        });
	    
		
		btnSignUp.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent=new Intent();
	        		intent.setClass(UserMenu.this, SignUpClickAdd.class);
	        		startActivity(intent);
		
	        	}
	        });
		
		btnFlyer.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(UserMenu.this, FlyerNew.class);
        		startActivity(intent);
	
        	}
        });
		
	     
	}

	private void init() {
		btnVote = (Button)findViewById(R.id.btn_vote);
		btnSignUp = (Button)findViewById(R.id.btn_sign_up);
		btnFlyer = (Button)findViewById(R.id.btn_nfc_flyer);
	}
	
	@Override 
    public void onBackPressed() { 
		Intent intent = getIntent();
		String flag = intent.getStringExtra("flag");
        super.onBackPressed(); 
        if(flag.equals("0"))
        {
        	Intent intent1 = new Intent();
        	intent1.setClass(UserMenu.this, MainActivity.class);
        	startActivity(intent1);
        	this.finish();
        }
    } 

}


