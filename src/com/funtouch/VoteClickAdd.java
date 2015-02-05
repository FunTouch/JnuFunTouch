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

public class VoteClickAdd extends Activity{

	private Button btnClickAdd = null;
	private Button btnUse = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vote_click_add);
		
		init();
		
		btnClickAdd.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent=new Intent();
	        		intent.setClass(VoteClickAdd.this, VoteEdit.class);
	        		startActivity(intent);
	         	}
	        });
		
		btnUse.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(VoteClickAdd.this, Beam.class);
        		startActivity(intent);
         	}
        });
		
		
	}

	private void init() {
		btnClickAdd = (Button)findViewById(R.id.btn_click_add);
		btnUse = (Button)findViewById(R.id.btn_del_vote);
		
	}
   
}



