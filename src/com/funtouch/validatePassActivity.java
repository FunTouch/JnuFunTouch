package com.funtouch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ValidatePassActivity extends FragmentActivity{
	ViewPager viewPager = null;
	public Cookie application ; 
	FragmentPagerAdapter adapter = null;
	Fragment activityerFragment = null;
	Fragment joinerFragment = null;
	List<Fragment> datas = null;
	TextView tv_activityer = null;
	TextView tv_joiner = null;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_validatepass);
		showOverflowMenu();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		init();
	}
	
	
	public void showOverflowMenu(){
		try {  
	        ViewConfiguration config =ViewConfiguration.get(this);  
	        Field menuKeyField = ViewConfiguration.class.
	        getDeclaredField("sHasPermanentMenuKey");  
	        if(menuKeyField != null) {  
	         menuKeyField.setAccessible(true);  
	         menuKeyField.setBoolean(config, false);  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}



@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_overflow, menu);
		return super.onCreateOptionsMenu(menu);
	}




@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case android.R.id.home:
			this.finish();
			break;
		case R.id.menu_aboutus:
			
			break;
		case R.id.menu_help:
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	public void init(){
		viewPager = (ViewPager)findViewById(R.id.MyViewPager);
		activityerFragment = new ActivityerFragment();
		joinerFragment = new joinerFragment();
		tv_activityer = (TextView)findViewById(R.id.tv_activityer);
		tv_activityer.setTextColor(Color.MAGENTA);
		tv_joiner = (TextView)findViewById(R.id.tv_joiner);
		datas = new ArrayList<Fragment>();
		datas.add(activityerFragment);
		datas.add(joinerFragment);
		adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return datas.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return datas.get(arg0);
			}
		};
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				resetColor();
				switch(arg0){
				case 0:
					tv_activityer.setTextColor(Color.MAGENTA);
					break;
				case 1:
					tv_joiner.setTextColor(Color.MAGENTA);
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	public void resetColor(){
		tv_activityer.setTextColor(Color.BLACK);
		tv_joiner.setTextColor(Color.BLACK);
	}
	
	class ActivityerFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return inflater.inflate(R.layout.fragment_activityer, container, false);
		}
	}
	class joinerFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.fragment_joiner, container, false);
			Button btn_getAward = (Button)view.findViewById(R.id.btn_getAward);
			btn_getAward.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(ValidatePassActivity.this, GetAward.class);
					startActivity(intent);
				}
			});
			return view;
		}
	}
}
