package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SignUpUse extends Activity {

	private Button btnStop;
	private ImageView imgUsing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_use);
		
		init();
		
		btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SignUpUse.this, SignUpEdit.class);
        		startActivity(intent);
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		btnStop = (Button)findViewById(R.id.btn_stop);
		imgUsing = (ImageView)findViewById(R.id.img_using);
	}
	

}
