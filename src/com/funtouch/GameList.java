package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameList extends Activity {

	private Button btnUndercover = null;
	private Button btnWerewolf = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		
		init();
		
		btnUndercover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GameList.this, GameUndercoverSetting.class);
				startActivity(intent);
			}
		});
		
		btnWerewolf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GameList.this, GameWerewolfSetting.class);
				startActivity(intent);
			}
		});
	}

	private void init() {
		btnUndercover = (Button) findViewById(R.id.btn_who_is_undercover);
		btnWerewolf = (Button) findViewById(R.id.btn_werewolf);
		
	}

}
