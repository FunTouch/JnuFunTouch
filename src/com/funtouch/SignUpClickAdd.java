package com.funtouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class SignUpClickAdd extends MenuHavingActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ImageButton btnClickAdd = null;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_click_add);
		btnClickAdd = (ImageButton) findViewById(R.id.btn_click_add);
		btnClickAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SignUpClickAdd.this, SignUpEdit.class);
				startActivity(intent);
			}
		});

	}

}



