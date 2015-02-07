package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;

public class FunTouchProfile extends Activity {

	TableRow tbrTag = null;
	TableRow tbrApp = null;
	TableRow tbrTeam = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funtouch_profile);
		
		init();
		
		tbrTag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FunTouchProfile.this, FunTouchTag.class);
				startActivity(intent);
			}
		});
		tbrApp.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FunTouchProfile.this, FunTouchAppIntroduction.class);
				startActivity(intent);
			}
			
		});
		
	}

	private void init() {
		tbrTag = (TableRow)findViewById(R.id.tbr_nfc_tag);
		tbrApp = (TableRow)findViewById(R.id.tbr_nfc_app);
		tbrTeam = (TableRow)findViewById(R.id.tbr_team);

	}

}
