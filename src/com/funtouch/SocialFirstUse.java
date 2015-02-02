package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

public class SocialFirstUse extends Activity {
	private Button btnOk;
	private Button btnNo;
	private CheckBox ckbFirstUse;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_first_use);
		
		init();
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SocialFirstUse.this, SocialCreateCardMenu.class);
        		startActivity(intent);
			}
		});
		
		btnNo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SocialFirstUse.this, CardTransport.class);
        		startActivity(intent);
			}
		});
		
		//���ò�����ʾ
		ckbFirstUse.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (ckbFirstUse.isChecked())
				{
					//MainActivity.setSocialStatus(false);
				}
				else
				{
					//MainActivity.setSocialStatus(true);
				}
				
			}			
		});
	}

	private void init() {
		btnOk = (Button)findViewById(R.id.btn_ok);
		btnNo = (Button)findViewById(R.id.btn_no);
		ckbFirstUse = (CheckBox)findViewById(R.id.ckb_no_hint);
	}

}
