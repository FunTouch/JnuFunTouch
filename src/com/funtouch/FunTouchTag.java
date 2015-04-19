package com.funtouch;
import java.lang.reflect.Field;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class FunTouchTag extends MenuHavingActivity {
	int selectedId = 0;
	RadioGroup  LabelGroup = null;
	RadioButton rB_noPaperPost = null;
	RadioButton rB_wifi = null;
	RadioButton rB_personalInfo = null;
	RadioButton rB_meetingSilence = null;
	RadioButton rB_newBookInfo = null;
	RadioButton rB_move = null;
	Button detailsButton = null;
	Button goBuyButton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funtouch_nfc_tag);
		init();

		LabelGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(rB_noPaperPost.getId() == checkedId){
					selectedId = 0;
				}
				if(rB_wifi.getId() == checkedId) {
					selectedId = 1;
				}
				if(rB_personalInfo.getId() == checkedId){
					selectedId = 2;
				}
				if(rB_meetingSilence.getId() == checkedId){
					selectedId = 3;
				}
				if(rB_newBookInfo.getId() == checkedId){
					selectedId = 4;
				}
				if(rB_move.getId() == checkedId){
					selectedId = 5;
				}
			}
		});
		
		detailsButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FunTouchTag.this, FunTouchTagDetails.class);
				intent.putExtra("selectedId", selectedId);
				startActivity(intent);
			}
			
		});
		
	}

	
	
	private void init() {
		detailsButton = (Button)findViewById(R.id.btn_details);
		goBuyButton = (Button)findViewById(R.id.btn_goBuy);
		LabelGroup = (RadioGroup)findViewById(R.id.radioGroup);
		rB_noPaperPost = (RadioButton)findViewById(R.id.rB_noPaperPost);
		rB_wifi = (RadioButton)findViewById(R.id.rB_wifi);
		rB_personalInfo = (RadioButton)findViewById(R.id.rB_personalInfo);
		rB_meetingSilence = (RadioButton)findViewById(R.id.rB_meetingSilence);
		rB_newBookInfo = (RadioButton)findViewById(R.id.rB_newBookInfo);
		rB_move = (RadioButton)findViewById(R.id.rB_move);
	}
}
