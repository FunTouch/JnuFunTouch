package com.funtouch;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.funtouch.Data;
import com.funtouch.UserInfo;

public class RegistActName extends Activity{
	private EditText registActName;
	private String name = null;
	public SharedPreferences user;
	private Data application;
	private String alluserinfos ;
	//Vector<UserInfo> userInfos = new Vector<UserInfo>();
	//List<UserInfo> userInfos= new ArrayList<UserInfo>();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Button btnOk = null;
		user = this.getSharedPreferences("user", Context.MODE_PRIVATE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_act_name);
		btnOk = (Button)findViewById(R.id.btn_ok);
		registActName = (EditText) findViewById(R.id.edit_act_name);
		application = (Data) this.getApplicationContext(); 
		btnOk.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		if(registActName.getText().toString().trim().equals("") )
	        			showToast("请输入活动名称");
	        		else{
	        		name = registActName.getText().toString();
	        		saveSPUser(application.getUserName(),application.getPassword(),application.getUserMailbox(),application.getUserClass(),application.getUserPhone(),application.getTemp(),name);
	        		//showToast(application.getTemp());
	        		//Intent intent=new Intent();
	        		//intent.setClass(RegistActName.this, Login.class);
	        		//startActivity(intent);
	        		}
		
	        	}
	        });
	}
	
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	// MD5加密，32位 
	public static String MD5(String str) { 
		MessageDigest md5 = null; 
		try { 
			md5 = MessageDigest.getInstance("MD5"); 
		} catch (Exception e) { 
			e.printStackTrace(); 
			return ""; 
		} 
		char[] charArray = str.toCharArray(); 
		byte[] byteArray = new byte[charArray.length]; 
		for (int i = 0; i < charArray.length; i++) { 
			byteArray[i] = (byte) charArray[i]; 
		} 
		byte[] md5Bytes = md5.digest(byteArray); 
		StringBuffer hexValue = new StringBuffer(); 
		for (int i = 0; i < md5Bytes.length; i++) { 
			int val = ((int) md5Bytes[i]) & 0xff; 
			if (val < 16) { 
				hexValue.append("0"); 
			} 
			hexValue.append(Integer.toHexString(val)); 
	} 
		return hexValue.toString(); 
	}

	private void saveSPUser(String userName, String password, String mb, String userclass, String uphone, String organ, String actname)
	{
		if(application.info.isEmpty()||(!application.info.isEmpty()&& (alluserinfos == null || alluserinfos.length()<=0)))
		{
			String save = user.getString("member", "");
			if (save != "")
				alluserinfos = save;
		}
		int flag = checkUser(userName,password,mb,userclass,uphone,organ,actname);// 检查是否已存在相同用户信息
		List<UserInfo> info = application.getInfo();
		if (flag == 1)
		{
		for (UserInfo userinfos: info)
		{	
			String name = userinfos.getName();
			String userinfo = userinfos.getName() + "/" + (userinfos.getPassword()) + "/" + userinfos.getMailbox() + "/" + userinfos.getUserClass() + "/" + userinfos.getUserPhone() + "/" + userinfos.getRegistOrgan() + "/" + userinfos.getRegistActName();
			if (alluserinfos == null)
			{
				alluserinfos = userinfo;
			} 
			else if(name.equals(userName))
			{
				alluserinfos += "," + userinfo;
			}		
		}
		

		Editor editor = user.edit();// 编辑器记录
		editor.putString("member", alluserinfos);
		editor.commit();// 编辑器提交保存
		Intent intent=new Intent();
		intent.setClass(RegistActName.this, Login.class);
		startActivity(intent);
		}
	}

  	public int checkUser(String userName, String password, String mb, String userclass, String uphone, String organ, String actname)
  	{
  		int flag = 0,flag1 = 0;
  		if (alluserinfos == null || alluserinfos.length() <= 0)
  		{
  			UserInfo userInfo = new UserInfo();
  			userInfo.initialize(userName, MD5(password), mb, userclass, uphone, organ, actname);
  			application.getInfo().add(userInfo);
  			showToast("注册成功,返回登陆页面");
  			flag1 = 1;
  			return flag1;
  		}
  		else // 有数据
		{	
			if (alluserinfos.contains(","))// 判断有无, 逗号代表用户每个用户分割点
			{
				String[] users = alluserinfos.split(",");
				for (int i=0; i < users.length; i++)
				{
					String str = users[i];
					String[] user = str.split("/");
					if (user[0].equals(userName))
		  			{	
		  				flag = 1;
		  				if (flag >= 0)
				  		{// 已存在
							showToast("此用户名已存在!请重新注册!");
				  			flag1 = 0;
				  			//return flag1;
							Intent intent=new Intent();
			        		intent.setClass(RegistActName.this, RegistInfo.class);
			        		startActivity(intent);
			        		
				  		}
		  				break;
		  			}
					
					
				}
				if(flag == 0)
				{
					UserInfo userInfo = new UserInfo();
			    	userInfo.initialize(userName, MD5(password), mb, userclass, uphone, organ, actname);
			    	application.getInfo().add(userInfo);
			    	showToast("注册成功,返回登陆页面");
			    
		  			flag1 = 1;
		  			return flag1;
				}
			}
			else if(!alluserinfos.contains(","))
				// 没有, 代表只有一个用户
			{
				UserInfo userinfo = new UserInfo();
				String[] user = alluserinfos.split("/"); 
				if (user[0].equals(userName))
		  		{// 已存在
		  			//application.getInfo().remove(position);
					showToast("此用户名已存在!请重新注册!");
		  			flag1 = 0;
		  			//return flag1;
					Intent intent=new Intent();
	        		intent.setClass(RegistActName.this, RegistInfo.class);
	        		startActivity(intent);
	        		//finish();
		  		}
				else{
					UserInfo userInfo = new UserInfo();
					userInfo.initialize(userName, MD5(password), mb, userclass, uphone, organ, actname);
					application.getInfo().add(userInfo);
					showToast("注册成功,返回登陆页面");
		  			flag1 = 1;
		  			return flag1;
				}
			}
			
		}
		return flag1;
  		
  	}
 }





