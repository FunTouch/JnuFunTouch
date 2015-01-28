package com.funtouch;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.funtouch.Data;


public class RegistOrgan extends Activity {
	private RadioGroup rg ;
	private RadioButton tgw,xsh,sl,gs,dq,rw,fy ;
	private String temp = "团工委" ;
	private Data application;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Button btnNext = null;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_organ);
		rg = (RadioGroup) findViewById(R.id.rg);
		tgw = (RadioButton) findViewById(R.id.tgw);
		xsh = (RadioButton) findViewById(R.id.xsh);
		sl = (RadioButton) findViewById(R.id.sl);
		gs = (RadioButton) findViewById(R.id.gs);
		dq = (RadioButton) findViewById(R.id.dq);
		rw = (RadioButton) findViewById(R.id.rw);
		fy = (RadioButton) findViewById(R.id.fy);
		btnNext = (Button) findViewById(R.id.btn_next);
		application = (Data) this.getApplicationContext(); 
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
			
			public void onCheckedChanged(RadioGroup group, int checkedId) { 
        	if(checkedId == R.id.tgw)
        		temp="团工委";
        	else if(checkedId==R.id.xsh)
        		temp="校区学生会";
        	else if(checkedId==R.id.sl)
        		temp="社联";
        	else if(checkedId==R.id.gs)
        		temp="国际商学院团委学生会";
        	else if(checkedId==R.id.dq)
        		temp="电气信息学院团委学生会";
        	else if(checkedId==R.id.rw)
        		temp="人文学院团委学生会";
        	else if(checkedId==R.id.fy)
        		temp="翻译学院团委学生会";
        	application.setTemp(temp);
        }
		});
		application.setTemp(temp);
		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				
				
				Intent intent = new Intent();
				intent.setClass(RegistOrgan.this, RegistActName.class);
				startActivity(intent);
			}
		});
		
	}
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
     
        




