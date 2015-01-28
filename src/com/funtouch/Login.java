package com.funtouch;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.funtouch.UserInfo;
import com.funtouch.MD5;

public class Login extends Activity {
	//private SharedPreferences read ;
	private EditText userName, password;
	public Cookie application ; 
	//public Data application;
	//List<UserInfo> userLogin= new ArrayList<UserInfo>();
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	

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
     
     
		Button btnLogin = null;
		Button btnRegist = null;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		userName = (EditText) findViewById(R.id.edit_UserName);
		password = (EditText) findViewById(R.id.edit_UserPassword);
		btnLogin = (Button) findViewById(R.id.btn_login);
		//application = (Data) this.getApplicationContext(); 
		//userLogin = getSPUser();
		
		//��֤�Ƿ��Ѿ���¼
		if(cookie!=null)			
		{
			Intent intent = new Intent();
			intent.setClass(Login.this, UserMenu.class);
			intent.putExtra("flag", "0");
			startActivity(intent);
			this.finish(); 
		}
		
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(userName.getText().toString().trim().equals("")||password.getText().toString().trim().equals("")){
					showToast("�������˻�������");
				}
				else{
					int flag = dataRetriever.login(userName.getText().toString(),MD5.Encode(password.getText().toString()));
					if(flag == 200)
					{
						showToast("��¼�ɹ�");
						Intent intent = new Intent();
						intent.setClass(Login.this, UserMenu.class);
						intent.putExtra("flag", "0");
						startActivity(intent);
						finish();  
					}
					else if(flag == 410)
					{
						showToast("��Ч���û���");
					}	
					else if(flag == 411)
					{
						showToast("�������,��������������");
					}
				}
			}
		});
		
		btnRegist = (Button) findViewById(R.id.btn_regist);
		btnRegist.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Login.this, RegistInfo.class);
				startActivity(intent);
				finish();

			}
		});
	}
	
	//��ʾ��
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	public void onBackPressed() { 
        super.onBackPressed(); 
        Intent intent = new Intent();
		intent.setClass(Login.this, MainActivity.class);
		startActivity(intent);
		this.finish();       
    } 

}


     
        



