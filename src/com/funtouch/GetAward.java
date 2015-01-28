package com.funtouch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class GetAward extends Activity {

	private Button btnOk = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.award);
		
		init();
	}

	private void init() {
		btnOk = (Button) findViewById(R.id.btn_ok);
		
	}

}
