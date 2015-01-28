package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SeeActivity extends Activity {

	private Button btnGetAward = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.see_activity);
		
		init();
		
		btnGetAward.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(SeeActivity.this, GetAward.class);
				startActivity(intent);
			}
		});
		
	}
	private void init() {
		btnGetAward = (Button) findViewById(R.id.btn_award);
		
	}

}
