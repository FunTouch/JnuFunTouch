package com.funtouch;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;


public class FunTouchTag extends Activity {

	TableRow tbrWify = null;
	TableRow tbrMode = null;
	TableRow tbrOther = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funtouch_nfc_tag);
		
		init();
		
		tbrWify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FunTouchTag.this, FunTouchTagWify.class);
				startActivity(intent);
			}
		});
		
		tbrOther.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FunTouchTag.this, FunTouchTagOther.class);
				startActivity(intent);
			}
		});
		
	}

	private void init() {
		tbrWify = (TableRow)findViewById(R.id.tbr_wify);
		tbrMode = (TableRow)findViewById(R.id.tbr_mode);
		tbrOther = (TableRow)findViewById(R.id.tbr_other);

	}
}
