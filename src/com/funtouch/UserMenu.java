package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserMenu extends Activity{

	private Button btnVote = null;
	private Button btnSignUp = null;
	private Button btnFlyer = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_menu);
		 
		init();
		
		
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

}


