package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FlyerSee extends Activity {

	private Button btnUse = null;
	private Button btnDelete = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flyer_see);
		
		init();
		btnUse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlyerSee.this, FlyerUse.class);
				startActivity(intent);
			}
		});
		
	}

	private void init() {
		btnUse = (Button) findViewById(R.id.btn_del_vote);
		btnDelete = (Button) findViewById(R.id.btn_delete);
		
	}

}
