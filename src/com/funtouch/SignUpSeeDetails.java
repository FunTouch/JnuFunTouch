package com.funtouch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class SignUpSeeDetails extends Activity {

	private ListView lsvDetails = null;
	private Button btnExport = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_see_detials);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		lsvDetails = (ListView)findViewById(R.id.lsv_details);
		
		btnExport = (Button)findViewById(R.id.btn_export);
	}

}
