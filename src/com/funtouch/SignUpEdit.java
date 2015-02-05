package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignUpEdit extends Activity{
	
	//define variable
	private Button btnOk = null;
	private Button btnEdit = null;
	private Button btnUse = null;
	private Button btnDelete = null;
	private Button btnSee = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_edit);
		
		init();
		
		btnOk.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent=new Intent();
	        		intent.setClass(SignUpEdit.this, SignUpClickAdd.class);
	        		startActivity(intent);
	         	}
	        });
		
		btnSee.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SignUpEdit.this, SignUpSeeDetails.class);
        		startActivity(intent);
			}
		});
		
		btnUse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SignUpEdit.this, SignUpUse.class);
        		startActivity(intent);
			}
		});
	
	}

	private void init() {
		// TODO Auto-generated method stub
		//button
		btnOk = (Button)findViewById(R.id.btn_ok);
		btnUse = (Button)findViewById(R.id.btn_del_vote);
		btnEdit = (Button)findViewById(R.id.btn_edit);
		btnDelete = (Button)findViewById(R.id.btn_del);
		btnSee = (Button)findViewById(R.id.btn_see);
	

	}
}
