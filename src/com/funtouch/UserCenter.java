package com.funtouch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserCenter extends Activity{
	
	private Button btnLogoff = null;
	private Button btnRevBeam = null;
	private Button btnChangePass = null;
	private Button btnUpdateInfo = null;
	private Button btnSignUpBeam = null;
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	
	List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
	
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_center);
		
		btnLogoff = (Button) findViewById(R.id.btn_logoff);
		btnRevBeam = (Button)findViewById(R.id.btn_rev_beam);
		btnChangePass = (Button)findViewById(R.id.btn_change_pass);
		btnUpdateInfo = (Button)findViewById(R.id.btn_update_info);	
		btnSignUpBeam = (Button)findViewById(R.id.btn_sign_up_beam);
		
		btnLogoff.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		showDialog();
        	}
        });
		
		btnChangePass.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){   		
        		Intent intent=new Intent();
        		intent.setClass(UserCenter.this, ChangePass.class);	
        		startActivity(intent);
        		finish();
        	}
        });
		
		btnUpdateInfo.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){   		
        		Intent intent=new Intent();
        		intent.setClass(UserCenter.this, UpdateInfo.class);	
        		startActivity(intent);
        		finish();
        	}
        });
		
		btnRevBeam.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		intent.setClass(UserCenter.this, RevVoteBeam.class);
        		startActivity(intent);
        	}
        });
		
		btnSignUpBeam.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		intent.setClass(UserCenter.this, SignUpBeam.class);
        		startActivity(intent);
        	}
        });
		
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
        		intent.setClass(UserCenter.this, MainActivity.class);	
        		startActivity(intent);
        		finish();
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
