package com.funtouch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ActMenu extends MenuHavingActivity{

	private ImageButton btnVote = null;
	private ImageButton btnSignUp = null;
	private ImageButton btnFlyer = null;
	private Button btnActVali = null;	
	
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	private String act_id;
	List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
	private DataRetriever dataRetriever = new DataRetriever();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_menu);
		 
		init();
		
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		
		btnVote.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent = new Intent();
	        		intent.setClass(ActMenu.this, VoteEdit.class);
	        		intent.putExtra("act_id", act_id);
	        		startActivity(intent);
	        	}
	        });	
	    	
		btnSignUp.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent=new Intent();
	        		intent.setClass(ActMenu.this, SignUpEdit.class);
	        		intent.putExtra("act_id", act_id);
	        		startActivity(intent);
		
	        	}
	        });
		
		btnFlyer.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(ActMenu.this, FlyerNew.class);
        		startActivity(intent);
	
        	}
        });
		
		btnActVali.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(ActMenu.this, ActVali.class);
        		intent.putExtra("act_id", act_id);
        		startActivity(intent);
        	}
        });
		
			
	     
	}
	
	private void init() {
		btnVote = (ImageButton)findViewById(R.id.btn_vote);
		btnSignUp = (ImageButton)findViewById(R.id.btn_sign_up);
		btnFlyer = (ImageButton)findViewById(R.id.btn_nfc_flyer);
		btnActVali = (Button)findViewById(R.id.btn_act_vali);
		
		
	}
	
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	

}


