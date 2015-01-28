package com.funtouch;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class FlyerList extends Activity {

	private Button btnNewFlyer = null;
	private Button btnSeeFlyer = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flyer_list);
		
		init();
		
		btnNewFlyer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlyerList.this, FlyerNew.class);
				startActivity(intent);
			}
		});
		
		btnSeeFlyer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlyerList.this, FlyerSee.class);
				startActivity(intent);
			}
		});
		
	}

	private void init() {
		btnNewFlyer = (Button) findViewById(R.id.btn_new_flyer);
		btnSeeFlyer =  (Button) findViewById(R.id.btn_see_flyer);
		
	}

}
