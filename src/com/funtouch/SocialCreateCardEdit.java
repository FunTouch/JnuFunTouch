package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SocialCreateCardEdit extends Activity {
	private Button btnOk;
	private Button btnExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_create_card_edit_info);
		
		init();
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SocialCreateCardEdit.this, CardTransportActivity.class);
        		startActivity(intent);
			}
		});
		
	}

	private void init() {
		btnOk = (Button)findViewById(R.id.btn_ok);
		btnExit = (Button)findViewById(R.id.btn_exit);
	}

}
