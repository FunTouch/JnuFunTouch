package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FlyerNew extends Activity {

	private Button btnImportImg = null;
	private Button btnAddDetail = null;
	private Button btnSelectStyle = null;
	private Button btnOk = null;
	private Button btnDelete = null;
	private Button btnPreview = null;
	//private Button btnReturn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_flyer);
		
		init();

		btnSelectStyle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlyerNew.this, FlyerNewSelectStyle.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_flyernew, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	private void init() {
		btnImportImg = (Button) findViewById(R.id.btn_import_image);
		btnAddDetail = (Button) findViewById(R.id.btn_add_detail);
		btnSelectStyle = (Button) findViewById(R.id.btn_select_style);
		btnOk = (Button) findViewById(R.id.btn_ok);
		btnDelete = (Button) findViewById(R.id.btn_delete);
		btnPreview = (Button) findViewById(R.id.btn_preview);
		
	}

}
