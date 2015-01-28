package com.funtouch;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import android.os.StrictMode;
import com.funtouch.UserInfo;

import java.security.MessageDigest;
import java.util.*;

import com.funtouch.Data;


public class RegistInfo extends Activity{
	private EditText userName, password,passwordAgain, userMailbox, userClass, userPhone;
	//public Data application;
	private List<Speaker> listSpeaker;
	private DataRetriever dataRetriever = new DataRetriever();
	private SimpleAdapter adapter;
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectAll()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
     StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());
     
		Button btnNext=null;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_info);
		userName = (EditText) findViewById(R.id.edit_UserName1);
		password = (EditText) findViewById(R.id.edit_Password);
		passwordAgain = (EditText) findViewById(R.id.edit_PasswordAgain);
		userMailbox = (EditText) findViewById(R.id.edit_Mailbox);
		userClass = (EditText) findViewById(R.id.edit_Class);
		userPhone = (EditText) findViewById(R.id.edit_Phone);
		btnNext=(Button)findViewById(R.id.btn_next);
		//application = (Data) this.getApplicationContext(); 
		btnNext.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		if(userName.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")
	        				||passwordAgain.getText().toString().trim().equals("")||userMailbox.getText().toString().trim().equals("")
	        				||userClass.getText().toString().trim().equals("")||userPhone.getText().toString().trim().equals("")){
						showToast("请输入全部信息..");
					}
	        		else if(!password.getText().toString().equals(passwordAgain.getText().toString())){
	        			showToast("两次输入的密码不一致,请重新输入");
	        		}
	        		else if(password.getText().length()<6){
	        			showToast("密码不符合规则,请重新输入");
	        		}
	        		else if(!userMailbox.getText().toString().contains("@")){
	        			showToast("邮箱格式不符合规则,请重新输入");
	        		}
	        		else if(!userClass.getText().toString().contains("-")){
	        			showToast("班级格式不符合规则,请重新输入");
	        		}
	        		else if(userPhone.getText().length()<11){
	        			showToast("电话号码不符合规则,请重新输入");
	        		}
	        		else{
	        			//application.setUserName(userName.getText().toString());
	        			//application.setPassword(password.getText().toString());
	        			//application.setUserMailbox(userMailbox.getText().toString());
	        			//application.setUserClass(userClass.getText().toString());
	        			//application.setUserPhone(userPhone.getText().toString());
	        			//Intent intent=new Intent();
	        			//intent.setClass(RegistInfo.this, RegistOrgan.class);
	        			//startActivity(intent);
	        			int flag = dataRetriever.regist(userName.getText().toString(),MD5.Encode(password.getText().toString()),
	        					userMailbox.getText().toString(),userClass.getText().toString(),userPhone.getText().toString());
	        			if(flag == 200)
	        			{
	        				showToast("注册成功,返回登陆页面!");
	        				Intent intent=new Intent();
	        				intent.setClass(RegistInfo.this, Login.class);
	        				startActivity(intent);
	        			}
	        			if(flag == 401)
	        			{
	        				showToast("此用户名已存在!请重新注册!");
	        			}
	        			if(flag == 0)
	        			{
	        				showToast("异常错误!");
	        			}
	        			}
	     
	}
	        });
		
	}
	// 显示Toast
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	
}
