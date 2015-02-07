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
	
	
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	private String act_id;
	List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_menu);
		 
		init();
		
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		
		//objectList = (List<ActDetailsInfo>) getIntent().getSerializableExtra("ListObject");
		
		btnVote.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent = new Intent();
	        		intent.setClass(UserMenu.this, VoteEdit.class);
	        		intent.putExtra("act_id", act_id);
	        		startActivity(intent);
	        	}
	        });
		
		
	    	
		btnSignUp.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent=new Intent();
	        		intent.setClass(UserMenu.this, SignUpEdit.class);
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


