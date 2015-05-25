package com.funtouch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SpySeeRole extends MenuHavingActivity {
	public Cookie application ;
	private TextView civilian , spy = null;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	private int room_id;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spy_see_role);
		Intent intent1 = getIntent();
		room_id = intent1.getIntExtra("room_id", 0);
		
		civilian = (TextView)findViewById(R.id.tv_civilian);
		spy = (TextView)findViewById(R.id.tv_spy);
		
		Spy roles = dataRetriever.getAllRole(room_id, cookie);
		if (roles.getCode().equals("200"))
		{
			civilian.setText(roles.getCommons()+"");
			spy.setText(roles.getSpys());
		}
		else
			showToast("获取角色列表失败!");
	}
	
	//提示类
  	public void showToast(String msg){
  		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
  	}

}
