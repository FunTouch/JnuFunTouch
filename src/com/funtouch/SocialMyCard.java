package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SocialMyCard extends Activity {
	private Button btnAddContact;
	private Button btnExport;
	private Button btnUse;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_my_card);
		
		init();
		
		btnAddContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SocialMyCard.this, SocialCreateCardMenu.class);
        		startActivity(intent);
			}
		});
		
		btnUse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SocialMyCard.this, SocialCardUse.class);
        		startActivity(intent);
			}
		});
	}

	private void init() {
		btnAddContact = (Button)findViewById(R.id.btn_add_contact);
		btnExport = (Button)findViewById(R.id.btn_export);
		btnUse = (Button)findViewById(R.id.btn_use);
	}

}
