package com.funtouch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class RegistInfo extends MenuHavingActivity{
	private EditText userName, password,passwordAgain, userMailbox, userClass, userPhone, realname, qq, about_me;
	//public Data application;
	private boolean wifi,internet = true;
	private List<Act> listSpeaker;
	private DataRetriever dataRetriever = new DataRetriever();
	private SimpleAdapter adapter;
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ImageButton btnNext=null;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_info);
		userName = (EditText) findViewById(R.id.edit_UserName1);
		password = (EditText) findViewById(R.id.edit_Password);
		passwordAgain = (EditText) findViewById(R.id.edit_PasswordAgain);
		userMailbox = (EditText) findViewById(R.id.edit_Mailbox);
		userClass = (EditText) findViewById(R.id.edit_Class);
		userPhone = (EditText) findViewById(R.id.edit_Phone);
		realname = (EditText) findViewById(R.id.edt_realname);
		qq = (EditText) findViewById(R.id.edt_qq);
		about_me = (EditText) findViewById(R.id.edt_about_me);
		
		btnNext=(ImageButton)findViewById(R.id.btn_next);
		//application = (Data) this.getApplicationContext(); 
		btnNext.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);  
					wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
					internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
					
					int flag0 = 0;
					if(wifi|internet){  
						flag0++;
					}
					else{  
						showToast("请检查网络状况!"); 
					}
					if(flag0!=0)
					{
					if(userName.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")
	        				||passwordAgain.getText().toString().trim().equals("")){
						showToast("请输入完整的注册信息!");
					}
	        		else if(!password.getText().toString().equals(passwordAgain.getText().toString())){
	        			showToast("两次输入的密码不一致!");
	        		}
	        		else if(password.getText().length()<6){
	        			showToast("密码长度不符合规则!");
	        		}
	        		else if(!userMailbox.getText().toString().contains("@") && !userMailbox.getText().toString().isEmpty()){
	        			showToast("邮箱格式不符合规则!");
	        		}
	        		else if( !userClass.getText().toString().contains("-")&& !userClass.getText().toString().isEmpty()){
	        			showToast("班级格式不符合规则!");
	        		}
	        		else if(userPhone.getText().length()<11 && !userPhone.getText().toString().isEmpty()){
	        			showToast("电话号码格式不符合规则!");
	        		}
	        		else{
		        			int flag = dataRetriever.regist(userName.getText().toString(),realname.getText().toString(),MD5.Encode(password.getText().toString()),
		        					userMailbox.getText().toString(),userClass.getText().toString(),userPhone.getText().toString(),qq.getText().toString(),about_me.getText().toString());
		        			if(flag == 200)
		        			{
		        				showToast("注册成功!");
		        				Intent intent=new Intent();
		        				intent.setClass(RegistInfo.this, Login.class);
		        				startActivity(intent);
		        				finish();
		        			}
		        			else if(flag == 415)
		        			{
		        				showToast("该用户名已存在!");
		        			}
		        			else if(flag == 0)
		        			{
		        				showToast("注册失败!");
		        			}
	        			}
					}
	        	}
	        });
		
	}
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	
}
