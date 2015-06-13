package com.funtouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class UsefulTag extends MenuHavingActivity{
	private Button btnWiFi = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.useful_tag);
		
		btnWiFi = (Button) findViewById(R.id.btnWiFi);
		
		btnWiFi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(UsefulTag.this, NfcWiFiAssistant.class);
				startActivity(intent);
			}
		});
		
	}

}
