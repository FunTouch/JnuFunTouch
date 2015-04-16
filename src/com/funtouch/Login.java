package com.funtouch;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.funtouch.UserInfo;
import com.funtouch.MD5;

public class Login extends MenuHavingActivity {
	//private SharedPreferences read ;
	private EditText userName, password;
	private static final int TIME_OUT = 0; 
    private static final int SUCCESS = 1; 
    // 超时的时限为5秒 
    private static final int TIME_LIMIT = 5000; 
	public Cookie application ; 
	private int flag = 0;
	private boolean wifi,internet = true;
	//public Data application;
	//List<UserInfo> userLogin= new ArrayList<UserInfo>();
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	private ProgressDialog pd;
	Timer timer; 
    Thread thread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Button btnLogin = null;
		Button btnRegist = null;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		userName = (EditText) findViewById(R.id.edit_UserName);
		password = (EditText) findViewById(R.id.edit_UserPassword);
		btnLogin = (Button) findViewById(R.id.btn_login);		
		
		//用户已登录
		if(cookie!=null)			
		{
			Intent intent = new Intent();
			intent.setClass(Login.this, UserCenter.class);
			intent.putExtra("flag", "0");
			startActivity(intent);
			this.finish(); 
		}
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
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
					
					if(userName.getText().toString().trim().equals("")||password.getText().toString().trim().equals("")){
						showToast("请输入用户名和密码!");
					}
					else{
						pd= ProgressDialog.show(Login.this, "登陆", "正在登陆…");
						thread = new Thread() {  
		                    @Override  
		                    public void run() { 
		                    	 flag = dataRetriever.login(userName.getText().toString(),MD5.Encode(password.getText().toString())); 
		                    	 Message msgSuc = new Message(); 
		                         msgSuc.what = SUCCESS; 
		                         handler.sendMessage(msgSuc);
		                    }  
		  
		                };
		                thread.start();
						timer = new Timer(); 
		                timer.schedule(new TimerTask() { 
		                    @Override 
		                    public void run() { 
		                        sendTimeOutMsg(); 
		                    } 
		                }, TIME_LIMIT); 
						
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

			}
		});
	}
	
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	private Handler handler =new Handler(){
		   @Override
		   //当有消息发送出来的时候就执行Handler的这个方法
		   public void handleMessage(Message msg){
			   switch (msg.what) { 
			   case TIME_OUT: 
	                //打断线程 
	                thread.interrupt(); 
	                pd.dismiss(); 
	                showToast("登陆超时"); 
	                break; 
			   case SUCCESS: 
	                //取消定时器 
	                timer.cancel(); 
	                pd.dismiss(); 
	                if(flag == 200)
					{
						showToast("登陆成功");
						Intent intent = new Intent();
						intent.setClass(Login.this, UserCenter.class);
						startActivity(intent);
						finish();  
					}
					else if(flag == 412)
					{
						showToast("认证错误!");
					}	
					else if(flag == 411)
					{
						showToast("密码错误,请重新输入密码");
					}
	                break; 
	            default: 
	                break; 
			   }
		      super.handleMessage(msg);
		      //只要执行到这里就关闭对话框
		      pd.dismiss();
		      
		   }
	};
	
	//向handler发送超时信息 
    private void sendTimeOutMsg() { 
        Message timeOutMsg = new Message(); 
        timeOutMsg.what = TIME_OUT; 
        handler.sendMessage(timeOutMsg); 
    }
	
}


     
        




