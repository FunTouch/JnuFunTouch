package com.funtouch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.funtouch.R.layout;
import com.funtouch.util.SystemBarTintManager;
import com.funtouch.util.SystemBarTintManager.SystemBarConfig;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ActMenu extends MenuHavingActivity{

	private ImageButton btnVote = null;
	private ImageButton btnSignUp = null;
	private ImageButton btnFlyer = null;
	private Button btnActVali = null;	
	
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	private String act_id;
	List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
	private DataRetriever dataRetriever = new DataRetriever();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.royalblue);
            SystemBarConfig config = tintManager.getConfig();
		}
		setContentView(R.layout.act_menu);
		init();
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		
		btnVote.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent = new Intent();
	        		intent.setClass(ActMenu.this, VoteEdit.class);
	        		intent.putExtra("act_id", act_id);
	        		startActivity(intent);
	        	}
	        });	
	    	
		btnSignUp.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent=new Intent();
	        		intent.setClass(ActMenu.this, SignUpEdit.class);
	        		intent.putExtra("act_id", act_id);
	        		startActivity(intent);
		
	        	}
	        });
		
		btnFlyer.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(ActMenu.this, FlyerNew.class);
        		startActivity(intent);
	
        	}
        });
		
		btnActVali.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(ActMenu.this, ActVali.class);
        		intent.putExtra("act_id", act_id);
        		startActivity(intent);
        	}
        });
		
			
	     
	}
	
	private void init() {
		btnVote = (ImageButton)findViewById(R.id.btn_vote);
		btnSignUp = (ImageButton)findViewById(R.id.btn_sign_up);
		btnFlyer = (ImageButton)findViewById(R.id.btn_nfc_flyer);
		btnActVali = (Button)findViewById(R.id.btn_act_vali);
		
		
	}
	
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	

}


