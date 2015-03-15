package com.funtouch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private ImageButton btnFunTouch = null;
	private ImageButton btnCardTransport = null;
	private ImageButton btnAct = null;
	private ImageButton btnSetting = null;
	private ImageButton btnGame = null;
	private Button btnLogin = null;
	private ImageButton btnValidatePass = null;
	private ImageButton btnExit = null;
	private boolean wifi,internet = true;
	public Cookie application ; 
	private long temptime = 0;
	String cookie = application.getInstance().getCookie();
	String name = application.getInstance().getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		
		ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);  
		wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
		internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
		if(wifi|internet){  
		    //执行相关操作  
		}else{  
			showDialog();  
		} 
		
		btnFunTouch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, FunTouchProfile.class);
				startActivity(intent);
				//finish();
			}
		});
		
		btnAct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AboutActivity.class);
				startActivity(intent);
				//finish();
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
		
		btnCardTransport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CardTransport.class);
				startActivity(intent);
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Login.class);
				startActivity(intent);
				//finish();
			}
		});
		
		if(cookie!=null)
		{
			btnLogin.setText(name);
		}
		
		btnValidatePass.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ValidatePassActivity.class);
				startActivity(intent);
			}
			
		});
		
		btnExit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showExitDialog();
			}
			
		});
	}
	
	public void showExitDialog(){
		new AlertDialog.Builder(MainActivity.this)
		.setTitle("退出")
		.setMessage("确定要退出软件吗嘤嘤嘤\n")
		.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//finish();
				System.exit(0);
				}
		})
		.setNegativeButton("取消", null)
		.show();
	}
		
		
	//双击返回键退出程序
	public boolean onKeyDown(int keyCode, KeyEvent event)   
	{  
	    // TODO Auto-generated method stub  	
	  
	    if((keyCode == KeyEvent.KEYCODE_BACK)&&(event.getAction() == KeyEvent.ACTION_DOWN))  
	    {  
	    	  
	        if(System.currentTimeMillis() - temptime >2000) // 2s内再按一次返回键退出   
	        {    
	            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_LONG).show();  
	            temptime = System.currentTimeMillis();  
	        }  
	        else {  
	               finish();   
	               System.exit(0); //程序正常退出  
	        }  
	             
	        return true;   
	  
	    }  
	    return super.onKeyDown(keyCode, event);  
	}  
	
	private void init() {
		btnFunTouch = (ImageButton) findViewById(R.id.btn_funtouch);
		btnAct = (ImageButton) findViewById(R.id.btn_about_activity);
		btnCardTransport = (ImageButton) findViewById(R.id.btnCardTransport);
		btnSetting = (ImageButton) findViewById(R.id.btn_setting);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnValidatePass = (ImageButton) findViewById(R.id.btn_validate_pass);
		btnExit = (ImageButton) findViewById(R.id.btn_exit);
	}
	
	
	//提示类
		public void showToast(String msg){
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
	
	
	
	
}
