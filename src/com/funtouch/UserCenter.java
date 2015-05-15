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

public class UserCenter extends MenuHavingActivity{
	
	private ImageButton btnLogoff = null;
	private ImageButton btnRevBeam = null;
	private ImageButton btnChangePass = null;
	private ImageButton btnUpdateInfo = null;
	private ImageButton btnSignUpBeam = null;
	private ImageButton btnMyCode = null;
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	
	List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
	
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_center);
		
		//btnLogoff = (ImageButton) findViewById(R.id.btn_logoff);
		//btnRevBeam = (ImageButton)findViewById(R.id.btn_rev_beam);
		btnChangePass = (ImageButton)findViewById(R.id.btn_change_pass);
		btnUpdateInfo = (ImageButton)findViewById(R.id.btn_update_info);	
		//btnSignUpBeam = (ImageButton)findViewById(R.id.btn_sign_up_beam);
		btnMyCode = (ImageButton)findViewById(R.id.btn_my_code);
		
		/*btnLogoff.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		showDialog();
        	}
        });*/
		
		btnMyCode.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(UserCenter.this, MyCode.class);
        		startActivity(intent);
        	}
        });
		
		btnChangePass.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){   		
        		Intent intent=new Intent();
        		intent.setClass(UserCenter.this, ChangePass.class);	
        		startActivity(intent);
        	}
        });
		
		btnUpdateInfo.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){   		
        		Intent intent=new Intent();
        		intent.setClass(UserCenter.this, UpdateInfo.class);	
        		startActivity(intent);
        	}
        });
		
		/*btnRevBeam.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		intent.setClass(UserCenter.this, MyVote.class);
        		startActivity(intent);
        	}
        });
		
		btnSignUpBeam.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		intent.setClass(UserCenter.this, MySignUp.class);
        		startActivity(intent);
        	}
        });*/
		
	}
	
	public void showDialog(){
		new AlertDialog.Builder(UserCenter.this)
		.setTitle("注销登陆")
		.setMessage("确定要注销登陆吗?")
		.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				application.getInstance().setCookie(null); 
				Intent intent=new Intent();
        		intent.setClass(UserCenter.this, Login.class);	
        		startActivity(intent);
        		finish();
        		MainActivity.instance.finish();
			}	
		})
		.setNegativeButton("取消", null)
		.show();
	}
	
	public void onBackPressed() { 
        super.onBackPressed(); 
        Intent intent = new Intent();
        intent.setClass(UserCenter.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    } 

}
