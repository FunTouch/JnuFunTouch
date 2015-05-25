package com.funtouch;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateInfo extends MenuHavingActivity{
	public Cookie application ; 
	private Button btnSubmitUpdate = null;
	private EditText edtUpdateName, edtUpdateSno, edtUpdateMailbox, edtUpdateGrade, edtUpdatePhone, edtUpdateQQ, edtAboutMe = null;
	private DataRetriever dataRetriever = new DataRetriever();
	private Map<String,String> info = null;
	private Map<String,String> update_info = new HashMap<String,String>();
	private String name,sno,qq,grade,mailbox,phone,about_me = null;
	String cookie = application.getInstance().getCookie();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
  // TODO Auto-generated method stub
  		super.onCreate(savedInstanceState);
  		setContentView(R.layout.update_info);
  		
  		edtUpdateName = (EditText) findViewById(R.id.edt_update_name);
  		edtUpdateSno = (EditText) findViewById(R.id.edt_update_sno);
  		edtUpdateMailbox = (EditText) findViewById(R.id.edt_update_mailbox);
  		edtUpdateGrade = (EditText) findViewById(R.id.edt_update_grade);
  		edtAboutMe = (EditText) findViewById(R.id.edt_about_me);
  		edtUpdatePhone = (EditText) findViewById(R.id.edt_update_phone);
  		edtUpdateQQ = (EditText) findViewById(R.id.edt_update_qq);
  		btnSubmitUpdate = (Button)findViewById(R.id.btn_submit_update);
  		
  		info = dataRetriever.getInfo(cookie);
 		
  		if(info.get("code").toString().equals("200"))
  		{
  			showToast("获取个人信息成功");
  			if(!info.get("name").toString().equals("null"))
  			{
  				edtUpdateName.setText(info.get("name").toString());			
  			}
  			name = info.get("name").toString();
  			if(!info.get("sno").toString().equals("null"))
  			{
  				edtUpdateSno.setText(info.get("sno").toString());
  			}
  			sno = info.get("sno").toString();
  			if(!info.get("qq").toString().equals("null"))
  			{
  				edtUpdateQQ.setText(info.get("qq").toString());
  				  			}
  			qq = info.get("qq").toString();
  			if(!info.get("grade").toString().equals("null"))
  			{
  				edtUpdateGrade.setText(info.get("grade").toString());
  				  			}
  			grade = info.get("grade").toString();
  			if(!info.get("mailbox").toString().equals("null"))
  			{
  				edtUpdateMailbox.setText(info.get("mailbox").toString());
  				  			}
  			mailbox = info.get("mailbox").toString();
  			if(!info.get("phone").toString().equals("null"))
  			{
  				edtUpdatePhone.setText(info.get("phone").toString());
  				  			}
  			phone = info.get("phone").toString();
  			if(!info.get("about_me").toString().equals("null"))
  			{
  				edtAboutMe.setText(info.get("about_me").toString());
  				  			}
  			about_me = info.get("about_me").toString();
  			
  			btnSubmitUpdate.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		if(edtUpdateSno.getText().toString().length() < 10 && !edtUpdateSno.getText().toString().isEmpty()){
	        			showToast("学号格式不符合规则!");
	        		}	
	        		else if(!edtUpdateMailbox.getText().toString().contains("@" )&& !edtUpdateMailbox.getText().toString().trim().equals("")){
	        			showToast("邮箱格式不符合规则!");
	        		}
	        		else if(!edtUpdateGrade.getText().toString().contains("-") && !edtUpdateGrade.getText().toString().trim().equals("")){
	        			showToast("班级格式不符合规则!");
	        		}
	        		else if(edtUpdatePhone.getText().length() < 11 && !edtUpdatePhone.getText().toString().trim().equals("")){
	        			showToast("电话号码格式不符合规则!");
	        		}
	        		else{
	        			if(!name.equals(null))
	        				update_info.put("name", edtUpdateName.getText().toString());
	        			else
	        				update_info.put("name", "null");
	        			if(!sno.equals(null))
	        				update_info.put("sno", edtUpdateSno.getText().toString());
	        			else
	        				update_info.put("sno", "null");
	        			if(!mailbox.equals(null))
	        				update_info.put("mailbox", edtUpdateMailbox.getText().toString());
	        			else
	        				update_info.put("mailbox", "null");
	        			if(!grade.equals(null))
	        				update_info.put("grade", edtUpdateGrade.getText().toString());
	        			else
	        				update_info.put("grade", "null");
	        			if(!phone.equals(null))
	        				update_info.put("phone", edtUpdatePhone.getText().toString());
	        			else
	        				update_info.put("phone", "null");
	        			if(!qq.equals(null))
	        				update_info.put("qq", edtUpdateQQ.getText().toString());
	        			else
	        				update_info.put("qq", "null");
	        			if(!about_me.equals(null))
	        				update_info.put("about_me", edtAboutMe.getText().toString());
	        			else
	        				update_info.put("about_me", "null");
	        			
	        			int flag = dataRetriever.updateInfo(cookie, update_info);
	        			if(flag == 200)
	        			{
	        				showToast("信息更新完成!");
	        				Intent intent=new Intent();
	        				intent.setClass(UpdateInfo.this, UserCenter.class);
	        				startActivity(intent);
	        				finish();
	        			}
	        			else if(flag == 440)
	        			{
	        				showToast("有无效的参数");
	        			}        			
	        		}
	        	}
  			});			
  		}
  		else if(info.get("code").toString().equals("412"))
  			showToast("获取个人信息失败!");

	}	
	
	//提示类
		private void showToast(CharSequence msg) {
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		}
		

	

}
