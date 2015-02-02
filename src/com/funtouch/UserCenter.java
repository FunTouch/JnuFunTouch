package com.funtouch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserCenter extends Activity{
	
	private Button btnLogoff = null;
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	
	List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
	
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_center);
		
		btnLogoff = (Button) findViewById(R.id.btn_logoff);
		
		btnLogoff.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){

        		application.getInstance().setCookie(null);    		
        		Intent intent=new Intent();
        		intent.setClass(UserCenter.this, MainActivity.class);	
        		startActivity(intent);
        		finish();
        	}
        });
		
	}
	public void onBackPressed() { 
        super.onBackPressed(); 
        Intent intent = new Intent();
        intent.setClass(UserCenter.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    } 

}
