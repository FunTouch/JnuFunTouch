package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameUndercoverSetting extends Activity {

	private Button btnStart = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_who_is_undercover_setting);
		
		init();
		
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GameUndercoverSetting.this, GameUndercoverGetRole.class);
				startActivity(intent);
			}
		});
	}

	private void init() {
		btnStart = (Button) findViewById(R.id.btn_start);
		
	}

}
