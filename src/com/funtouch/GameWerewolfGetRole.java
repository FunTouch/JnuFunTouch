package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameWerewolfGetRole extends Activity {
	private Button btnRoleDetail = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_werewolf_get_role);
		
		init();
		
		btnRoleDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GameWerewolfGetRole.this, GameWerewolfDetail.class);
				startActivity(intent);
			}
		});
	}

	private void init() {
		btnRoleDetail = (Button) findViewById(R.id.btn_role_detail);
		
	}

}
