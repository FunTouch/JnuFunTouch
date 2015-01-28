package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SocialCreateCardMenu extends Activity {
	private Button btnDesignCard;
	private Button btnImportContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_create_card_menu);
		
		init();
		
		btnDesignCard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SocialCreateCardMenu.this, SocialCreateCardEdit.class);
        		startActivity(intent);
			}
		});
		
		btnImportContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SocialCreateCardMenu.this, SocialCreateCardImportContact.class);
        		startActivity(intent);
			}
		});
	}

	private void init() {
		btnDesignCard = (Button)findViewById(R.id.btn_design_card);
		btnImportContact = (Button)findViewById(R.id.btn_import_contact);
	}

}
