package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePass extends Activity{
	public Cookie application ; 
	private EditText old_pass, new_pass, new_conf_pass = null;
	private Button btnPassSubmit = null;
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
     
     	// TODO Auto-generated method stub
  		super.onCreate(savedInstanceState);
  		setContentView(R.layout.change_pass);
     
     	btnPassSubmit = (Button) findViewById(R.id.btn_pass_submit);
     	old_pass = (EditText) findViewById(R.id.edt_old_pass);
     	new_pass = (EditText) findViewById(R.id.edt_new_pass);
     	new_conf_pass = (EditText) findViewById(R.id.edt_new_conf_pass);
     	
     	btnPassSubmit.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){  
        		if(old_pass.getText().toString().trim().equals("")){
					showToast("请输入原密码!");
				}
        		else if(new_pass.getText().toString().trim().equals("")||new_conf_pass.getText().toString().trim().equals(""))
        		{
        			showToast("请输入新密码!");
        		}
        		else if(!new_pass.getText().toString().equals(new_conf_pass.getText().toString())){
        			showToast("两次输入的密码不一致!");
        		}
        		else if(new_pass.getText().length()<6){
        			showToast("密码长度不符合规则!");
        		}
        		else{
        			int flag = dataRetriever.changePass(cookie, MD5.Encode(old_pass.getText().toString()), MD5.Encode(new_pass.getText().toString()));
        			if(flag == 200)
        			{
        				showToast("修改成功,请重新登录!");
        				Intent intent=new Intent();
        				application.getInstance().setCookie(null);
                		intent.setClass(ChangePass.this, Login.class);	
                		startActivity(intent);
                		finish();
        			}
        			else if(flag == 441)
        			{
        				showToast("原密码错误!");
        			}
        		}
  		
        	}
        });
     	
	}
	
	public void onBackPressed() { 
        super.onBackPressed(); 
        Intent intent = new Intent();
		intent.setClass(ChangePass.this, UserCenter.class);
		startActivity(intent);
		this.finish();       
    } 
	
	
	//提示类
		private void showToast(CharSequence msg) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}

}