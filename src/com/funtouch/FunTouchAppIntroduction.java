package com.funtouch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FunTouchAppIntroduction extends Activity{
	TextView tv_appInduction = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appintroduction);
		tv_appInduction = (TextView)findViewById(R.id.tv_appInduction);
	}
}
