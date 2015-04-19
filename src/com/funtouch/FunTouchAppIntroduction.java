package com.funtouch;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.TextView;

public class FunTouchAppIntroduction extends MenuHavingActivity{
	TextView tv_appInduction = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appintroduction);
		tv_appInduction = (TextView)findViewById(R.id.tv_appInduction);
	}
	
	
}
