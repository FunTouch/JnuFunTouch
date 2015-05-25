package com.funtouch;

import java.lang.reflect.Field;

import com.funtouch.util.SystemBarTintManager;
import com.funtouch.util.SystemBarTintManager.SystemBarConfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	//private ImageButton btnFunTouch = null;
	//private ImageButton btnCardTransport = null;
	private ImageButton btnAct = null;
	private ImageButton btnSetting = null;
	private ImageButton btnGame = null;
	//private Button btnLogin = null;
	private Button btnWho = null;
	private ImageButton btnValidatePass = null;
	private ImageButton btnExit = null;
	//private ImageButton btnGame = null;
	private ImageButton btnUserCenter = null;
	private ImageButton btnCampusLife = null;

	private boolean wifi,internet = true;
	public Cookie application ; 
	private long temptime = 0;
	static MainActivity instance;
	String cookie = application.getInstance().getCookie();
	String name = application.getInstance().getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectAll()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .build());
        
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
		setContentView(R.layout.activity_main);
		instance=this;
		showOverflowMenu();
		init();
		
		ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);  
		wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
		internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
		
		/*btnFunTouch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, FunTouchProfile.class);
				startActivity(intent);
				//finish();
			}
		});*/
		
		btnAct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ActCenter.class);
				startActivity(intent);
			}
		});
		
		/*btnGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GameList.class);
				startActivity(intent);
			}
		});*/
		
		/*btnCardTransport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CardTransport.class);
				startActivity(intent);
			}
		});*/
		
		btnUserCenter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, UserCenter.class);
				startActivity(intent);
			}
		});
		
		btnCampusLife.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CampusLife.class);
				startActivity(intent);
			}
		});
		
		/*btnValidatePass.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ValidatePassActivity.class);
				startActivity(intent);
			}
			
		});*/
		
		/*btnExit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showExitDialog();
			}
			
		});
		
		btnWho.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, WhoIsTheSpy.class);
				startActivity(intent);
			}
			
		});
		});*/
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
		case R.id.menu_aboutus:
			
			break;
		case R.id.menu_help:
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void showExitDialog(){
		new AlertDialog.Builder(MainActivity.this)
		.setTitle("╮(╯﹏╰）╭退出")
		.setMessage("  o(>﹏<)o嘤嘤嘤真的要退出我吗?")
		.setPositiveButton("狠心确定", new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//finish();
				System.exit(0);
				}
		})
		.setNegativeButton("再耍一会", null)
		.show();
	}
		
		
	//双击返回键退出程序
	public boolean onKeyDown(int keyCode, KeyEvent event)   
	{  
	    // TODO Auto-generated method stub  	
		
	    if((keyCode == KeyEvent.KEYCODE_BACK)&&(event.getAction() == KeyEvent.ACTION_DOWN))  
	    {  
	    	showExitDialog(); 
	        /*if(System.currentTimeMillis() - temptime >2000) // 2s内再按一次返回键退出   
	        {    
	            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_LONG).show();  
	            temptime = System.currentTimeMillis();  
	        }  
	        else {  
	               finish();   
	               System.exit(0); //程序正常退出  
	        }  
	             
	        return true;  */ 
	  
	    }  
	    return super.onKeyDown(keyCode, event);  
	}  
	
	private void init() {
		//btnFunTouch = (ImageButton) findViewById(R.id.btn_funtouch);
		btnAct = (ImageButton) findViewById(R.id.btn_actCenter);
		//btnCardTransport = (ImageButton) findViewById(R.id.btnCardTransport);
		btnSetting = (ImageButton) findViewById(R.id.btn_setting);

		//btnLogin = (Button) findViewById(R.id.btn_login);
		//btnValidatePass = (ImageButton) findViewById(R.id.btn_validate_pass);
		btnExit = (ImageButton) findViewById(R.id.btn_exit);

		btnUserCenter = (ImageButton) findViewById(R.id.btn_userCenter);
		btnCampusLife = (ImageButton) findViewById(R.id.btn_campusLife);
		//btnValidatePass = (ImageButton) findViewById(R.id.btn_validate_pass);
		//btnExit = (ImageButton) findViewById(R.id.btn_exit);
	}
	
	
	//提示类
		public void showToast(String msg){
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		}
		
		public void showDialog(){
			new AlertDialog.Builder(MainActivity.this)
			.setTitle("请检查网络状态")
			.setMessage("亲，网络连了么？")
			.setCancelable(false)
			.setPositiveButton("退出", new android.content.DialogInterface.OnClickListener(){
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
				
			})
			.show();
		}
		
		protected void onNewIntent(Intent intent) {

			super.onNewIntent(intent);
			MainActivity.this.recreate();
		}
	
		public void showOverflowMenu(){
			try {  
		        ViewConfiguration config = ViewConfiguration.get(this);  
		        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");  
		        if(menuKeyField != null) {  
		            menuKeyField.setAccessible(true);  
		            menuKeyField.setBoolean(config, false);  
		        }  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    }  
		}
	
	
}
